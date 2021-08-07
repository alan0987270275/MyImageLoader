/**
 * Thanks for Coil and James Ooi for the inspiration.
 */
package com.example.imageloaderlibrary

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark


/**
 * @param uri:  file path and url
 * @param activity: provide activity lifeCycle CoroutineScope to manage the coroutines
 * @see ImageView.loadAny */
fun ImageView.load(
    uri: String,
    activity: AppCompatActivity,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    imageLoader: ImageLoader = ImageLoader.with(context),
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadAny(uri, activity.lifecycleScope, dispatcher, imageLoader, builder)

/**
 * @param resourceId:  @DrawableRes
 * @param activity: provide CoroutineScope to manage the coroutines
 * @see ImageView.loadAny */
fun ImageView.load(
    resourceId: Int,
    activity: AppCompatActivity,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    imageLoader: ImageLoader = ImageLoader.with(context),
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadAny(resourceId, activity.lifecycleScope, dispatcher, imageLoader, builder)


/**
 * @param uri:  file path and url
 * @param fragment: provide Fragment lifeCycle CoroutineScope to manage the coroutines
 * @see ImageView.loadAny */
fun ImageView.load(
    uri: String,
    fragment: Fragment,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    imageLoader: ImageLoader = ImageLoader.with(context),
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadAny(
    uri,
    fragment.viewLifecycleOwner.lifecycleScope,
    dispatcher,
    imageLoader,
    builder
)

/**
 * @param resourceId:  @DrawableRes
 * @param fragment: provide Fragment CoroutineScope to manage the coroutines
 * @see ImageView.loadAny */
fun ImageView.load(
    resourceId: Int,
    fragment: Fragment,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    imageLoader: ImageLoader = ImageLoader.with(context),
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadAny(
    resourceId,
    fragment.viewLifecycleOwner.lifecycleScope,
    dispatcher,
    imageLoader,
    builder
)

/**
 * @param uri:  file path and url
 * @param lifecycleScope: provide Custom lifeCycle CoroutineScope to manage the coroutines
 * @see ImageView.loadAny */
fun ImageView.load(
    uri: String,
    lifecycleScope: CoroutineScope,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    imageLoader: ImageLoader = ImageLoader.with(context),
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadAny(uri, lifecycleScope, dispatcher, imageLoader, builder)

/**
 * @param resourceId:  @DrawableRes
 * @param lifecycleScope: provide Custom CoroutineScope to manage the coroutines
 * @see ImageView.loadAny */
fun ImageView.load(
    resourceId: Int,
    lifecycleScope: CoroutineScope,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    imageLoader: ImageLoader = ImageLoader.with(context),
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadAny(resourceId, lifecycleScope, dispatcher, imageLoader, builder)

/**
 * [ImageView.loadAny] is the type-unsafe version of [ImageView.load].
 *
 * @param resource The resource to load.
 * @param lifecycleScope The CoroutineScope to launch
 * @param dispatcher  To determines what thread the corresponding coroutine uses for its execution
 * @param imageLoader The [ImageLoader] that do the work.
 * @param builder An optional lambda to configure the request before it is being process.
 */
private fun <T> ImageView.loadAny(
    resource: T,
    lifecycleScope: CoroutineScope,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    imageLoader: ImageLoader = ImageLoader.with(context),
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    val imageRequest = ImageRequest.Builder()
        .apply(builder)
        .build()

    lifecycleScope.launch(dispatcher) {
        imageLoader.load(this@loadAny, resource, imageRequest)
    }
}


/**
 * Benchmark function
 */
@ExperimentalTime
fun ImageView.loadForBenchmark(
    url: String,
    name: String,
    lifecycleScope: CoroutineScope,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    imageLoader: ImageLoader = ImageLoader.with(context),
    builder: ImageRequest.Builder.() -> Unit = {},
    totalMark: TimeMark
) {
    val imageRequest = ImageRequest.Builder()
        .apply(builder)
        .build()

    lifecycleScope.launch(dispatcher) {
//        imageLoader.loadForBenchmark(this@loadForBenchmark, url, name, imageRequest, totalMark)
    }

}
