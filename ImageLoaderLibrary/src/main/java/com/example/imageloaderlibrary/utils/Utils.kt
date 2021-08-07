package com.example.imageloaderlibrary.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import okhttp3.Cache
import java.io.File


object Utils {

    private const val TAG = "ImageLoader"
    private const val CACHE_DIRECTORY_NAME = "ImageLoader-DiskCache"
    private const val MAX_DISK_CACHE_SIZE_BYTES = 250L * 1024 * 1024 // 250MB


    fun createDefaultCache(context: Context): Cache =
        Cache(File(context.cacheDir, CACHE_DIRECTORY_NAME), MAX_DISK_CACHE_SIZE_BYTES)

    fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {

        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) inSampleSize *= 2
        }

        return inSampleSize
    }

    fun getImageViewWidth(imageView: ImageView): Int {
        val params: ViewGroup.LayoutParams = imageView.layoutParams
        var width = 0
        if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            width = imageView.width // Get actual image width
        }
        if (width <= 0 && params != null) {
            width = params.width // Get layout width parameter
        }
        if (width <= 0) {
            width = getScreenWidth()
        }
        return width
    }

    fun getImageViewHeight(imageView: ImageView): Int {
        val params = imageView.layoutParams
        var height = 0
        if (params != null
            && params.height != ViewGroup.LayoutParams.WRAP_CONTENT
        ) {
            height = imageView.height // Get actual image height
        }
        if (height <= 0 && params != null) {
            // Get layout height parameter
            height = params.height
        }
        if (height <= 0) {
            height = getScreenHeight()
        }
        return height
    }

    private fun getScreenWidth(): Int =
        Resources.getSystem().displayMetrics.widthPixels


    private fun getScreenHeight(): Int =
        Resources.getSystem().displayMetrics.heightPixels

}