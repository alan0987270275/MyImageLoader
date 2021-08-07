package com.example.imageloaderlibrary.loader

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.cache.ReuseBitmapSet

interface Loader {

    /**
     * Fetch the image with path and the size of the imageView
     * @param path: the resource path: Url, local file path.
     * @param imageView: the target to load image into.
     * @param reuseBitmapSet: if set than can utilize reusable bitmap.
     */
    suspend fun fetchImage(
        path: String,
        imageView: ImageView,
        reuseBitmapSet: ReuseBitmapSet? = null
    ): Bitmap?
}