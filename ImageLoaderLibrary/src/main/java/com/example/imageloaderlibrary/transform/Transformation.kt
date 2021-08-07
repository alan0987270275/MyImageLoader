package com.example.imageloaderlibrary.transform

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.cache.ReuseBitmapSet

interface Transformation {

    /**
     * Apply the transformation to [input] and return the transformed [Bitmap].
     *
     * @param reuseBitmapSet A [BitmapPool] which can be used to request [Bitmap] instances.
     * @param input The input [Bitmap] to transform.
     * @return The transformed [Bitmap].
     */
    suspend fun transform(reuseBitmapSet: ReuseBitmapSet, input: Bitmap): Bitmap
}