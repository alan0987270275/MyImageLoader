package com.example.myimageloader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.imageloaderlibrary.ImageLoader

class HomeActivity : AppCompatActivity() {

    private lateinit var jumpToSingleViewButton: Button
    private lateinit var jumpToListButton: Button
    private lateinit var clearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        jumpToSingleViewButton = findViewById(R.id.jumpToSingleViewButton)
        jumpToListButton = findViewById(R.id.jumpToListButton)
        clearButton = findViewById(R.id.clearButton)

        jumpToSingleViewButton.setOnClickListener {
            val intent = Intent(this, SingleViewActivity::class.java)
            startActivity(intent)
        }

        jumpToListButton.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        clearButton.setOnClickListener {
            ImageLoader.with(this).clear()
        }

    }
}