package com.example.imageloaderlibrary.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.cache.ReuseBitmapSet

object ReuseBitmapUtil {

    private val TAG = ReuseBitmapUtil.javaClass.name

    fun addInBitmapOptions(options: BitmapFactory.Options, reusableBitmapSet: ReuseBitmapSet) {
        // inBitmap only works with mutable bitmaps, so force the decoder to
        // return mutable bitmaps.
        options.inMutable = true

        // Try to find a bitmap to use for inBitmap.
        reusableBitmapSet?.getReuseBitmap(options)?.also { inBitmap ->
            // If a suitable bitmap has been found, set it as the value of
            // inBitmap.
            options.inBitmap = inBitmap
        }
    }

    fun canUseForInBitmap(
        candidate: Bitmap, targetOptions: BitmapFactory.Options
    ): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val width = targetOptions.outWidth / targetOptions.inSampleSize
            val height = targetOptions.outHeight / targetOptions.inSampleSize
            val byteCount: Int = width * height * getBytesPerPixel(candidate.config)
            return try {
                byteCount <= candidate.allocationByteCount
            } catch (e: NullPointerException) {
                byteCount <= candidate.height * candidate.rowBytes
            }
        }
        return candidate.width == targetOptions.outWidth &&
                candidate.height == targetOptions.outHeight &&
                targetOptions.inSampleSize == 1
    }

    fun canUseForInBitmap(
        candidate: Bitmap, target: Bitmap
    ): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val width = target.width
            val height = target.height
            val byteCount: Int = width * height * getBytesPerPixel(candidate.config)
            return try {
                byteCount <= candidate.allocationByteCount
            } catch (e: NullPointerException) {
                byteCount <= candidate.height * candidate.rowBytes
            }
        }
        return candidate.width == target.width &&
                candidate.height == target.height
    }

    private fun getBytesPerPixel(config: Bitmap.Config): Int {
        var config: Bitmap.Config? = config
        if (config == null) {
            config = Bitmap.Config.ARGB_8888
        }
        return when (config) {
            Bitmap.Config.ALPHA_8 -> 1
            Bitmap.Config.RGB_565, Bitmap.Config.ARGB_4444 -> 2
            Bitmap.Config.ARGB_8888 -> 4
            else -> 4
        }
    }
}