package com.example.myimageloader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloaderlibrary.ImageLoader
import com.example.imageloaderlibrary.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemAdapter(
    private val imageList: ArrayList<String>,
    private val context: Context,
    private val imageLoader: ImageLoader,
    private val lifecycleCoroutineScope: LifecycleCoroutineScope
) :
    RecyclerView.Adapter<ItemAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false),
            imageLoader
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position], lifecycleCoroutineScope)
    }

    override fun getItemCount() = imageList.size

    fun shuffle(){
        imageList.shuffle()
    }

    class ImageViewHolder(view: View, private val imageLoader: ImageLoader) :
        RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById(R.id.itemImageView) as ImageView

        fun bind(url: String, lifecycleCoroutineScope: LifecycleCoroutineScope) {
            itemView.apply {
                imageView.load(url, lifecycleCoroutineScope) {
                    placeholder(R.drawable.ic_baseline_cloud_download_24)
                    error(R.drawable.ic_baseline_error_24)
                }
            }
        }
    }
}

