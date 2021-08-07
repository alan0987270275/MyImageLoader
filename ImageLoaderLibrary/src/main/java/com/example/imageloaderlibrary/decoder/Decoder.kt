package com.example.imageloaderlibrary.decoder

import android.graphics.Bitmap
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.cache.ReuseBitmapSet


interface Decoder {

    /**
     * Scale down the image before load into imageView
     */
    fun <T>scaleBitmap(
        resource: T,
        width: Int,
        height: Int,
        reuseBitmapSet: ReuseBitmapSet?
    ): Bitmap?

}