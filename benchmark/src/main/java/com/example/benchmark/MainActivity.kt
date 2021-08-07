package com.example.benchmark

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val LOAD_WITH_IMAGELOADER = 1
    private val LOAD_WITH_GLIDE = 2

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ItemAdapter(data, lifecycleScope, LOAD_WITH_IMAGELOADER)


        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            Log.d(TAG, "----------Refresh------------")
            swipeRefreshLayout.isRefreshing = false
            recyclerView.adapter = ItemAdapter(data, lifecycleScope, LOAD_WITH_IMAGELOADER)
        }

    }
}