package com.example.imageloaderlibrary.transform

import android.graphics.*
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.cache.ReuseBitmapSet

class CircleCropTransformation : Transformation {

    override suspend fun transform(reuseBitmapSet: ReuseBitmapSet, input: Bitmap): Bitmap {
        val output = reuseBitmapSet.getReuseBitmap(input, input.width, input.height)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, input.width, input.height)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawCircle(
            (input.width / 2).toFloat(), (input.height / 2).toFloat(),
            (Math.min(input.width, input.height) / 2).toFloat(), paint
        )
        paint.xfermode = XFERMODE
        canvas.drawBitmap(input, rect, rect, paint)

        return output
    }

    override fun equals(other: Any?) = other is CircleCropTransformation

    override fun hashCode() = javaClass.hashCode()

    override fun toString() = "CircleCropTransformation()"

    private companion object {
        val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }
}