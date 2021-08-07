package com.example.imageloaderlibrary.cache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import com.example.imageloaderlibrary.utils.ReuseBitmapUtil
import java.lang.ref.SoftReference
import java.util.*
import kotlin.collections.HashSet

class ReuseBitmapSet {

    private var reusableBitmaps: MutableSet<SoftReference<Bitmap>>? = null

    init {
        reusableBitmaps = Collections.synchronizedSet(HashSet<SoftReference<Bitmap>>())
    }

    fun add(bitmap: Bitmap) {
        if (bitmap.isMutable) {
            reusableBitmaps?.add(SoftReference(bitmap))
        }
    }

    fun clear() {
        reusableBitmaps?.clear()
    }

    // For decoding
    fun getReuseBitmap(options: BitmapFactory.Options) = get(options)

    // For Transformation
    fun getReuseBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap =
        get(bitmap) ?: Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    fun get(options: BitmapFactory.Options): Bitmap? {
        reusableBitmaps?.takeIf { it.isNotEmpty() }?.let { reusableBitmaps ->
            val iterator: MutableIterator<SoftReference<Bitmap>> = reusableBitmaps.iterator()
            while (iterator.hasNext()) {
                iterator.next().get()?.let { item ->
                    if (item.isMutable) {
                        if (ReuseBitmapUtil.canUseForInBitmap(item, options)) {
                            iterator.remove()
                            return item
                        }
                    } else {
                        iterator.remove()
                    }
                }
            }
        }
        return null
    }

    fun get(input: Bitmap): Bitmap? {
        reusableBitmaps?.takeIf { it.isNotEmpty() }?.let { reusableBitmaps ->
            val iterator: MutableIterator<SoftReference<Bitmap>> = reusableBitmaps.iterator()
            while (iterator.hasNext()) {
                iterator.next().get()?.let { item ->
                    if (item.isMutable) {
                        if (ReuseBitmapUtil.canUseForInBitmap(item, input)) {
                            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                item.reconfigure(input.width, input.height, input.config)
                                item
                            } else {
                                // From document:
                                // when possible. If the specified width and height are the same
                                // as the current width and height of the source bitmap, the
                                // source bitmap is returned and no new bitmap is created.
                                Bitmap.createScaledBitmap(item, input.width, input.height, true)
                            }
                        }
                    } else {
                        iterator.remove()
                    }
                }
            }
        }
        return null
    }


}