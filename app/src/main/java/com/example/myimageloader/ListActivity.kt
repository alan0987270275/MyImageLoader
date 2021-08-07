package com.example.myimageloader


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.imageloaderlibrary.ImageLoader

class ListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recyclerView = findViewById(R.id.recyclerView)
        refreshLayout = findViewById(R.id.refreshLayout)
        val list = createImageUrlList()
        adapter = ItemAdapter(list, this, ImageLoader.with(this), lifecycleScope)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
            adapter.shuffle()
            adapter.notifyDataSetChanged()
        }
    }

    private fun createImageUrlList(): ArrayList<String> {
        val imageUrlList = ArrayList<String>()
        for (i in 1..100) {
            imageUrlList.add("https://rickandmortyapi.com/api/character/avatar/$i.jpeg")
        }
        return imageUrlList
    }

}