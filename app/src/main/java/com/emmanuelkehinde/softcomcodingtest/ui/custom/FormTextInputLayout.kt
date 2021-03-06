package com.emmanuelkehinde.softcomcodingtest.ui.custom

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import com.emmanuelkehinde.softcomcodingtest.R
import com.emmanuelkehinde.softcomcodingtest.data.FIELD_IS_MANDATORY
import com.emmanuelkehinde.softcomcodingtest.data.PHONE_NUMBER_NOT_ACCURATE
import com.emmanuelkehinde.softcomcodingtest.data.model.Rule
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormTextInputLayout: TextInputLayout {

    var validationRule: List<Rule> = emptyList()
    var isMandatory: Boolean = false
    var expectedLength: Int = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun validate(): Boolean {
        var isValid = true

        val edtTextInput = this.findViewById<TextInputEditText>(R.id.edt_text_input)
        edtTextInput.text?.let {
            if (it.isEmpty() && this.isMandatory) {
                this.error = FIELD_IS_MANDATORY
                this.requestFocus()
                isValid = false
            } else if(edtTextInput.inputType == InputType.TYPE_CLASS_PHONE && it.length != this.expectedLength) {
                this.error = PHONE_NUMBER_NOT_ACCURATE
                this.requestFocus()
                isValid = false
            } else {
                this.error = null
            }
        }

        return isValid
    }

}