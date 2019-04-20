package com.emmanuelkehinde.softcomcodingtest.ui.form

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.emmanuelkehinde.softcomcodingtest.App
import com.emmanuelkehinde.softcomcodingtest.data.model.ElementType
import com.emmanuelkehinde.softcomcodingtest.data.model.Section
import com.emmanuelkehinde.softcomcodingtest.widget.*
import javax.inject.Inject

class FormInteractor {

    private val map = mutableMapOf<ElementType, FormWidget>()

    companion object {
        fun getInstance() = FormInteractor()
    }

    init {
        registerWidgets()
    }

    private fun registerWidgets() {
        map[ElementType.EMBEDDED_PHOTO] = ImageViewWidget()
        map[ElementType.TEXT] = EditTextWidget()
        map[ElementType.FORMATTED_NUMERIC] = EditTextWidget()
        map[ElementType.DATE_TIME] = EditTextWidget()
        map[ElementType.YES_NO] = RadioGroupWidget()
    }

    fun getFormElements(parent: ViewGroup, inflater: LayoutInflater, section: Section): ArrayList<View> {
        val elementViews = ArrayList<View>()

        elementViews.add(TextViewWidget().getView(parent, inflater, section.label))

        section.elements.forEach { element->
            map[element.type]?.getView(parent, inflater, element)?.let {
                elementViews.add(it)
            }
        }

        return elementViews
    }

}