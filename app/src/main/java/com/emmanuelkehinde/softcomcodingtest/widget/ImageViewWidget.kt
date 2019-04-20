package com.emmanuelkehinde.softcomcodingtest.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.emmanuelkehinde.softcomcodingtest.R
import com.emmanuelkehinde.softcomcodingtest.data.model.Element
import com.emmanuelkehinde.softcomcodingtest.util.ImageUtil

class ImageViewWidget: FormWidget {

    override fun getView(inflater: LayoutInflater, parent: ViewGroup, element: Element): View {
        val imageView = inflater.inflate(R.layout.layout_image_view, parent, false) as ImageView
        imageView.tag = element.uniqueId
        ImageUtil.loadImageFromUrl(element.file ?: "", imageView)
        return imageView
    }

}