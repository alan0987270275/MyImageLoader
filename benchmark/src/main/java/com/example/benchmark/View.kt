package com.example.benchmark

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

@OptIn(ExperimentalTime::class)
fun ImageView.loadImage(data: Response, totalMark: TimeMark) {

    val tag = "Glide"
    val mark = TimeSource.Monotonic.markNow()

    Glide.with(this).load(data.url)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(object : CustomTarget<Drawable>() {
        override fun onLoadCleared(placeholder: Drawable?) {
        }

        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            this@loadImage.setImageDrawable(resource)
            Log.d(tag, "Elapsed time: ${mark.elapsedNow()} ${data.name}")
            Log.d(tag, "Total time: ${totalMark.elapsedNow()}, ${data.name}")
        }
    })
}