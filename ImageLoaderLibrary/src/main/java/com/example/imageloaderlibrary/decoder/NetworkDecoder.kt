package com.example.imageloaderlibrary.decoder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.cache.ReuseBitmapSet
import com.example.imageloaderlibrary.utils.ReuseBitmapUtil
import com.example.imageloaderlibrary.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.BufferedSource
import java.io.File

class NetworkDecoder : Decoder {

    /**
     * Scale down the image download from network before load into imageView
     */
    suspend fun compressImage(
        bufferedSource: BufferedSource,
        imageView: ImageView,
        reuseBitmapSet: ReuseBitmapSet?
    ): Bitmap? = withContext(Dispatchers.IO) {
        scaleBitmap(
            bufferedSource,
            Utils.getImageViewWidth(imageView),
            Utils.getImageViewHeight(imageView),
            reuseBitmapSet
        ) ?: null
    }

    override fun <T> scaleBitmap(
        resource: T,
        width: Int,
        height: Int,
        reuseBitmapSet: ReuseBitmapSet?
    ): Bitmap? =
        BitmapFactory.Options().run {
            val bufferedSource = resource as BufferedSource
            inMutable = true
            // First decode with inJustDecodeBounds=true to check dimensions
            inJustDecodeBounds = true
            BitmapFactory.decodeStream(bufferedSource.peek().inputStream(), null, this)
            inSampleSize = Utils.calculateInSampleSize(this, width, height)
            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false

            // Reuse the bitmap in the compress section.
            if (reuseBitmapSet != null) {
                ReuseBitmapUtil.addInBitmapOptions(this, reuseBitmapSet)
            }
            BitmapFactory.decodeStream(bufferedSource.inputStream(), null, this)
        }
}