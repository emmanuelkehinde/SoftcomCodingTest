package com.emmanuelkehinde.softcomcodingtest.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.emmanuelkehinde.softcomcodingtest.R

class TextViewWidget {

    fun getView(inflater: LayoutInflater, parent: ViewGroup, label: String): View {
        val textView = inflater.inflate(R.layout.layout_text_view, parent, false) as TextView
        textView.text = label
        return textView
    }

}