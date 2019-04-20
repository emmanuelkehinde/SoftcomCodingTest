package com.emmanuelkehinde.softcomcodingtest.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioButton
import android.widget.RadioGroup
import com.emmanuelkehinde.softcomcodingtest.R
import com.emmanuelkehinde.softcomcodingtest.data.model.Rule

class FormRadioGroup: RadioGroup {

    var rules: List<Rule> = emptyList()
    var isMandatory: Boolean = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    fun validate(): Boolean {
        var isValid = true

        val rbYes = this.findViewById(R.id.rb_yes) as RadioButton
        val rbNo = this.findViewById(R.id.rb_no) as RadioButton
        if (!rbYes.isChecked && !rbNo.isChecked && this.isMandatory) {
            isValid = false
        }

        return isValid
    }
}