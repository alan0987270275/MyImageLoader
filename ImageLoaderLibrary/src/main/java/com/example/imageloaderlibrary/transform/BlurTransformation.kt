package com.example.imageloaderlibrary.transform

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.graphics.applyCanvas
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.cache.ReuseBitmapSet

@RequiresApi(18)
class BlurTransformation @JvmOverloads constructor(
    private val context: Context,
    private val radius: Float = DEFAULT_RADIUS,
    private val sampling: Float = DEFAULT_SAMPLING
) : Transformation {

    init {
        require(radius in 0.0..25.0) { "radius must be in [0, 25]." }
        require(sampling > 0) { "sampling must be > 0." }
    }

    override suspend fun transform(reuseBitmapSet: ReuseBitmapSet, input: Bitmap): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

        val scaledWidth = (input.width / sampling).toInt()
        val scaledHeight = (input.height / sampling).toInt()

        val output = reuseBitmapSet.getReuseBitmap(input, scaledWidth, scaledHeight)
        output.applyCanvas {
            scale(1 / sampling, 1 / sampling)
            drawBitmap(input, 0f, 0f, paint)
        }

        var script: RenderScript? = null
        var tmpInt: Allocation? = null
        var tmpOut: Allocation? = null
        var blur: ScriptIntrinsicBlur? = null
        try {
            script = RenderScript.create(context)
            tmpInt = Allocation.createFromBitmap(script, output, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT)
            tmpOut = Allocation.createTyped(script, tmpInt.type)
            blur = ScriptIntrinsicBlur.create(script, Element.U8_4(script))
            blur.setRadius(radius)
            blur.setInput(tmpInt)
            blur.forEach(tmpOut)
            tmpOut.copyTo(output)
        } finally {
            script?.destroy()
            tmpInt?.destroy()
            tmpOut?.destroy()
            blur?.destroy()
        }

        return output
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is BlurTransformation &&
                context == other.context &&
                radius == other.radius &&
                sampling == other.sampling
    }

    override fun hashCode(): Int {
        var result = context.hashCode()
        result = 31 * result + radius.hashCode()
        result = 31 * result + sampling.hashCode()
        return result
    }

    override fun toString(): String {
        return "BlurTransformation(context=$context, radius=$radius, sampling=$sampling)"
    }

    private companion object {
        private const val DEFAULT_RADIUS = 10f
        private const val DEFAULT_SAMPLING = 1f
    }
}