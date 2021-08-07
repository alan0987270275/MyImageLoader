package com.example.imageloaderlibrary


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.imageloaderlibrary.cache.MemoryLruCache
import com.example.imageloaderlibrary.loader.LocalFileLoader
import com.example.imageloaderlibrary.loader.NetworkLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ImageLoader(private val context: Context) {

    private val cache: MemoryLruCache = MemoryLruCache()
    private val networkLoader: NetworkLoader = NetworkLoader(context.applicationContext)
    private val fileLoader: LocalFileLoader = LocalFileLoader()

    companion object {
        private const val TAG = "ImageLoader"
        private var INSTANCE: ImageLoader? = null

        @Synchronized
        fun with(context: Context): ImageLoader {
            return INSTANCE ?: ImageLoader(context.applicationContext).also {
                INSTANCE = it
            }

        }
    }

    fun clear() {
        cache.clear()
        networkLoader.clear()
        Toast.makeText(context, "Clear the cache!!", Toast.LENGTH_SHORT).show()
    }

    suspend fun <T> load(imageView: ImageView, resource: T, imageRequest: ImageRequest) {
        when (resource) {
            is Int -> {
                loadImageViaLocalResource(imageView, resource, imageRequest)
            }
            is String -> {
                if (resource.startsWith("http")) {
                    loadImageViaUrl(imageView, resource, imageRequest)
                } else
                    loadImageViaLocalFile(imageView, resource, imageRequest)
            }
        }
    }

    private fun loadImageViaLocalResource(
        imageView: ImageView,
        id: Int,
        imageRequest: ImageRequest?
    ) {
        try {
            val d: Drawable? =
                ResourcesCompat.getDrawable(context.resources, id, null)
            d?.apply {
                imageView.setImageDrawable(d)
            }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, e.toString())
            if (imageRequest?.errorResId != null) {
                imageView.setImageResource(imageRequest.errorResId)
            }
            showErrorImage(imageView, imageRequest)
        }
    }

    private suspend fun loadImageViaLocalFile(
        imageView: ImageView,
        path: String,
        imageRequest: ImageRequest?
    ) {
        val bitmap = fileLoader.fetchImage(path, imageView)
        if (bitmap == null) {
            showErrorImage(imageView, imageRequest)
        } else {
            val finalResult = applyTransformation(bitmap, imageRequest)
            imageView.setImageBitmap(finalResult)
        }
    }

    private suspend fun loadImageViaUrl(
        imageView: ImageView,
        url: String,
        imageRequest: ImageRequest?
    ) {
        cache[url]?.also {
            val finalResult = applyTransformation(it, imageRequest)
            imageView.setImageBitmap(finalResult)
        } ?: run {

            showPlaceHolder(imageView, imageRequest)

            val bitmap = getFromNetwork(imageView, url)

            bitmap?.apply {
                val finalResult = applyTransformation(bitmap, imageRequest)
                imageView.setImageBitmap(finalResult)
            } ?: showErrorImage(imageView, imageRequest)

        }
    }

    private suspend fun getFromNetwork(imageView: ImageView, url: String): Bitmap? {
        return networkLoader.fetchImage(url, imageView, cache.reusableBitmapSet)?.apply {
            cache.put(url, this)
        }
    }

    private fun showErrorImage(imageView: ImageView, imageRequest: ImageRequest?) {
        imageRequest?.errorResId?.let {
            imageView.setImageResource(it)
        }
    }

    private fun showPlaceHolder(imageView: ImageView, imageRequest: ImageRequest?) {
        imageRequest?.placeholderResId?.let {
            imageView.setImageResource(it)
        }
    }

    private suspend fun applyTransformation(bitmap: Bitmap, imageRequest: ImageRequest?): Bitmap {
        return withContext(Dispatchers.IO) {
            imageRequest?.transformations!!.fold(bitmap) { bitmap, transformation ->
                transformation.transform(cache.reusableBitmapSet, bitmap)
            }
        }
    }


    /** Only for Benchmark, When use this function need to add @ExperimentalTime annotation marks to the class.**/
//    suspend fun loadForBenchmark(
//        imageView: ImageView,
//        resource: String,
//        name: String,
//        imageRequest: ImageRequest,
//        totalMark: TimeMark
//    ) {
//        val mark = TimeSource.Monotonic.markNow()
//        loadImageViaUrl(imageView, resource, imageRequest)
//        Log.d(TAG, "Elapsed time: ${mark.elapsedNow()} $name")
//        Log.d(TAG, "Total time: ${totalMark.elapsedNow()} $name")
//    }

}