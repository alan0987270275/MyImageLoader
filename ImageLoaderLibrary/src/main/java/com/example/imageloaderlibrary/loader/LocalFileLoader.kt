package com.example.imageloaderlibrary.loader

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.cache.ReuseBitmapSet
import com.example.imageloaderlibrary.decoder.LocalFileDecoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class LocalFileLoader : Loader {

    private val localFileDecoder = LocalFileDecoder()

    override suspend fun fetchImage(
        path: String,
        imageView: ImageView,
        reuseBitmapSet: ReuseBitmapSet?
    ): Bitmap? =
        withContext(Dispatchers.IO) {
            try {
                val imgFile = File(path)
                localFileDecoder.compressImage(imgFile, imageView)
            } catch (e: Exception) {
                null
            }
        }

}