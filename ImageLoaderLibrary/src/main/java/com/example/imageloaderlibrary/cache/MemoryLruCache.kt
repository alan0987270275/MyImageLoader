package com.example.imageloaderlibrary.cache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import com.example.imageloaderlibrary.utils.ReuseBitmapUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.ref.SoftReference
import java.util.*
import kotlin.collections.HashSet


class MemoryLruCache {
    private val TAG = MemoryLruCache::class.java.name
    private val cache: LruCache<String, Bitmap>
    val reusableBitmapSet = ReuseBitmapSet()

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 4

        cache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024
            }

            override fun entryRemoved(
                evicted: Boolean,
                key: String?,
                oldValue: Bitmap?,
                newValue: Bitmap?
            ) {
                super.entryRemoved(evicted, key, oldValue, newValue)
                if (oldValue != null) {
                    if (!oldValue.isRecycled) {
                        reusableBitmapSet.add(oldValue)
                    }
                }
            }
        }

    }

    fun put(url: String, bitmap: Bitmap) {
        cache.put(url, bitmap)
    }

    operator fun get(url: String): Bitmap? = cache.get(url)

    fun clear() {
        cache.evictAll()
        reusableBitmapSet?.clear()
    }
}