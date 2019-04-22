package com.emmanuelkehinde.softcomcodingtest.util

import android.widget.ImageView
import com.emmanuelkehinde.softcomcodingtest.R
import com.squareup.picasso.Picasso

class ImageUtil {
    companion object {
        fun loadImageFromUrl(imageUrl: String, imageView: ImageView) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.ic_image_black_24dp).into(imageView)
        }
    }
}