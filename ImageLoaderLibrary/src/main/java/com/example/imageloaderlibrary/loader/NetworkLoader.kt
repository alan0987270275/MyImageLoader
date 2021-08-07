package com.example.imageloaderlibrary.loader

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.cache.ReuseBitmapSet
import com.example.imageloaderlibrary.decoder.NetworkDecoder
import com.example.imageloaderlibrary.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class NetworkLoader(context: Context) : Loader {
    private val TAG = "NetworkLoader"
    private val cache = Utils.createDefaultCache(context)
    private val okHttpClient =
        OkHttpClient.Builder().cache(cache).build()
    private val networkDecoder = NetworkDecoder()

    override suspend fun fetchImage(
        path: String,
        imageView: ImageView,
        reuseBitmapSet: ReuseBitmapSet?
    ): Bitmap? {
        return withContext(Dispatchers.IO) {
            var res: Response? = null
            try {
                val req = Request.Builder().url(path).build()
                res = okHttpClient.newCall(req).execute()
                val bufferedSource = res.body?.source()

                bufferedSource?.let {
                    networkDecoder.compressImage(it, imageView, reuseBitmapSet)
                }

            } catch (e: Exception) {
                null
            } finally {
                res?.apply {
                    res.close()
                }
            }
        }
    }

    fun clear() {
        cache?.let {
            cache.evictAll()
        }
    }

}