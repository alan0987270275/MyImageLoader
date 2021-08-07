package com.example.myimageloader

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.imageloaderlibrary.ImageLoader
import com.example.imageloaderlibrary.load
import com.example.imageloaderlibrary.transform.BlurTransformation
import com.example.imageloaderlibrary.transform.CircleCropTransformation
import com.example.imageloaderlibrary.transform.GrayscaleTransformation
import com.example.imageloaderlibrary.transform.Transformation
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class SingleViewActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private val RC_STORAGE_PERM = 123
    private val TAG = "MainActivity"

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var newImageButton: Button
    private lateinit var applyButton: Button
    private lateinit var circleCropCheckBox: CheckBox
    private lateinit var grayscaleCheckBox: CheckBox
    private lateinit var blurCheckBox: CheckBox
    private var transformationList: ArrayList<Transformation> = arrayListOf()
    private var index = -1

    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_view)

        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)
        newImageButton = findViewById(R.id.newImageButton)
        applyButton = findViewById(R.id.applyImageButton)
        circleCropCheckBox = findViewById(R.id.circleCropCheckbox)
        grayscaleCheckBox = findViewById(R.id.grayScaleCheckbox)
        blurCheckBox = findViewById(R.id.blurCheckbox)

        askPermission()

        imageLoader = ImageLoader.with(this)

        newImageButton.setOnClickListener {
            loadNewImage()
        }

        applyButton.setOnClickListener {
            applyTransformation()
        }

    }

    override fun onRestart() {
        super.onRestart()
        imageView.setImageBitmap(null)
        textView.text = ""
    }

    private fun loadNewImage() {
        index = (index + 1) % testingUrl.size

        if (index == testingUrl.size - 1) {
            loadIntResource()
        } else {
            loadStringResource()
        }
    }

    private fun loadStringResource() {
        testingUrl[index].apply {
            imageView.load(testingUrl[index] as String, this@SingleViewActivity) {
                placeholder(R.drawable.ic_baseline_cloud_download_24)
                error(R.drawable.ic_baseline_error_24)
            }
            setText(this)
        }
    }

    private fun loadIntResource() {
        testingUrl[index].apply {
            imageView.load(testingUrl[index] as Int, this@SingleViewActivity) {
                placeholder(R.drawable.ic_baseline_cloud_download_24)
                error(R.drawable.ic_baseline_error_24)
            }
            setText(this)
        }
    }

    private fun setText(resource: Any) {
        when (resource) {
            is Int -> {
                textView.text = "Load R.drawable"
            }
            is String -> {
                if (resource.toString().startsWith("https"))
                    textView.text = "Load url resource"
                else
                    textView.text = "Load local file"
            }
        }
    }

    private fun applyTransformation() {
        imageView.load(testingUrl[index] as String, lifecycleScope) {
            placeholder(R.drawable.ic_baseline_cloud_download_24)
            error(R.drawable.ic_baseline_error_24)
            transformations(transformationList)
        }
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.circleCropCheckbox -> {
                    if (checked) {
                        transformationList.add(CircleCropTransformation())
                    } else {
                        transformationList.remove(CircleCropTransformation())
                    }
                }
                R.id.grayScaleCheckbox -> {
                    if (checked) {
                        transformationList.add(GrayscaleTransformation())
                    } else {
                        transformationList.remove(GrayscaleTransformation())
                    }
                }
                R.id.blurCheckbox -> {
                    if (checked) {
                        transformationList.add(BlurTransformation(this, 10F))
                    } else {
                        transformationList.remove(BlurTransformation(this, 10F))
                    }
                }
            }
        }
    }

    private fun askPermission() {
        // Ask for one permission
        EasyPermissions.requestPermissions(
            this,
            "Get permission for reading local file",
            RC_STORAGE_PERM,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }


}