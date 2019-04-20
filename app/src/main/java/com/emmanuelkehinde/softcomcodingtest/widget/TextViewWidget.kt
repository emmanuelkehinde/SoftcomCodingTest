package com.emmanuelkehinde.softcomcodingtest.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.emmanuelkehinde.softcomcodingtest.R
import com.emmanuelkehinde.softcomcodingtest.data.model.Element

class TextViewWidget {

    fun getView(parent: ViewGroup, inflater: LayoutInflater, label: String): View {
        val textView = inflater.inflate(R.layout.layout_text_view, parent, false) as TextView
        textView.text = label
        return textView
    }

}