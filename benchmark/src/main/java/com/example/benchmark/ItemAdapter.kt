package com.example.benchmark

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloaderlibrary.ImageLoader
import com.example.imageloaderlibrary.loadForBenchmark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

@OptIn(ExperimentalTime::class)
class ItemAdapter(
    private val imageList: List<Response>,
    private val lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val action: Int
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    @OptIn(ExperimentalTime::class)
    private val totalMark by lazy { TimeSource.Monotonic.markNow() }
    private val LOAD_WITH_IMAGELOADER = 1
    private val LOAD_WITH_GLIDE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.recycler_item))

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position], lifecycleCoroutineScope, action)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById(R.id.itemImageView) as ImageView

        fun bind(data: Response, lifecycleCoroutineScope: LifecycleCoroutineScope, action: Int) {
            itemView.apply {
                when (action) {
                    LOAD_WITH_IMAGELOADER -> {
                        imageView.loadForBenchmark(
                            data.url,
                            data.name,
                            lifecycleScope = lifecycleCoroutineScope,
                            totalMark = totalMark
                        )
                    }
                    LOAD_WITH_GLIDE -> {
                        imageView.loadImage(data, totalMark)
                    }
                }
            }
        }
    }
}