package com.example.imageloaderlibrary.decoder


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.cache.ReuseBitmapSet
import com.example.imageloaderlibrary.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class LocalFileDecoder : Decoder {


    /**
     * Scale down the local file image before load into imageView
     */
    suspend fun compressImage(
        file: File,
        imageView: ImageView,
    ): Bitmap? = withContext(Dispatchers.IO) {
        scaleBitmap(
            file,
            Utils.getImageViewWidth(imageView),
            Utils.getImageViewHeight(imageView),
            null
        ) ?: null
    }

    override fun <T> scaleBitmap(
        resource: T,
        width: Int,
        height: Int,
        reuseBitmapSet: ReuseBitmapSet?
    ): Bitmap? = BitmapFactory.Options().run {
        val file = resource as File
        inMutable = true
        // First decode with inJustDecodeBounds=true to check dimensions
        inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, this)
        inSampleSize = Utils.calculateInSampleSize(this, width, height)
        // Decode bitmap with inSampleSize set
        inJustDecodeBounds = false

        BitmapFactory.decodeFile(file.absolutePath, this)
    }
}