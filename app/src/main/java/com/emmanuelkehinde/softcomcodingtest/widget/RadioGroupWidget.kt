package com.emmanuelkehinde.softcomcodingtest.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.emmanuelkehinde.softcomcodingtest.R
import com.emmanuelkehinde.softcomcodingtest.data.model.Condition
import com.emmanuelkehinde.softcomcodingtest.data.model.Element
import com.emmanuelkehinde.softcomcodingtest.data.model.Value
import com.emmanuelkehinde.softcomcodingtest.ui.custom.FormRadioGroup

class RadioGroupWidget: FormWidget {

    override fun getView(inflater: LayoutInflater, parent: ViewGroup, element: Element): View {
        val radioGroup = inflater.inflate(R.layout.layout_radio_group, parent, false) as FormRadioGroup
        val tvRadioGroupLabel = radioGroup.findViewById(R.id.tv_radio_group_label) as TextView
        val rbYes = radioGroup.findViewById(R.id.rb_yes) as RadioButton
        val rbNo = radioGroup.findViewById(R.id.rb_no) as RadioButton

        radioGroup.tag = element.uniqueId
        radioGroup.rules = element.rules
        radioGroup.isMandatory = element.isMandatory ?: false

        tvRadioGroupLabel.text = element.label
        element.rules.forEach { rule->
            if (rule.condition == Condition.EQUALS && rule.value == Value.YES) {
                rbYes.isChecked = true
            } else rbNo.isChecked = true
        }

        return radioGroup
    }
}