package com.emmanuelkehinde.softcomcodingtest.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emmanuelkehinde.softcomcodingtest.data.model.Element

interface FormWidget {
    fun getView(parent: ViewGroup, inflater: LayoutInflater, element: Element): View
}