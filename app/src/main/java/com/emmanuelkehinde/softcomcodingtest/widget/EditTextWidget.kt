package com.emmanuelkehinde.softcomcodingtest.widget

import android.app.DatePickerDialog
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emmanuelkehinde.softcomcodingtest.R
import com.emmanuelkehinde.softcomcodingtest.data.PHONE_NUMBER_LENGTH
import com.emmanuelkehinde.softcomcodingtest.data.model.Element
import com.emmanuelkehinde.softcomcodingtest.data.model.ElementType
import com.emmanuelkehinde.softcomcodingtest.ui.custom.FormTextInputLayout
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class EditTextWidget: FormWidget {

    override fun getView(inflater: LayoutInflater, parent: ViewGroup, element: Element): View {
        val editText = inflater.inflate(R.layout.layout_edit_text, parent, false) as FormTextInputLayout
        val edtTextInput = editText.findViewById(R.id.edt_text_input) as TextInputEditText

        editText.tag = element.uniqueId
        editText.hint = element.label
        editText.isMandatory = element.isMandatory ?: false
        editText.validationRule = element.rules

        when {
            element.type == ElementType.TEXT -> {
                edtTextInput.inputType = InputType.TYPE_CLASS_TEXT
            }
            element.type == ElementType.FORMATTED_NUMERIC -> {
                edtTextInput.inputType = InputType.TYPE_CLASS_PHONE
                addLengthFilter(edtTextInput)
                edtTextInput.addTextChangedListener(onTextChangeListener(edtTextInput))
            }
            element.type == ElementType.DATE_TIME -> {
                edtTextInput.isFocusableInTouchMode = false
                edtTextInput.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    showDatePickerDialog(inflater, edtTextInput, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                }
            }
        }

        return editText
    }


    private fun addLengthFilter(edtTextInput: TextInputEditText) {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(PHONE_NUMBER_LENGTH)
        edtTextInput.filters = filterArray
    }

    private fun onTextChangeListener(edtTextInput: TextInputEditText): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = edtTextInput.text.toString()
                edtTextInput.removeTextChangedListener(this)

                if ((input.length == 4 || input.length == 8) && !input.endsWith("-")) {
                    val newText = edtTextInput.text.toString() + "-"
                    edtTextInput.setText(newText)
                    edtTextInput.setSelection(edtTextInput.text.toString().length)
                } else if ((input.length == 5 || input.length == 9) && input.endsWith("-")) {
                    if (input.length == 5) {
                        edtTextInput.setText(edtTextInput.text.toString().substring(0, 4))
                        edtTextInput.setSelection(edtTextInput.text.toString().length)
                    }
                    if (input.length == 9) {
                        edtTextInput.setText(edtTextInput.text.toString().substring(0, 8))
                        edtTextInput.setSelection(edtTextInput.text.toString().length)
                    }
                }

                edtTextInput.addTextChangedListener(this)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        }
    }

    private fun showDatePickerDialog(inflater: LayoutInflater, edtTextInput: TextInputEditText,
                                     calendarYear: Int, calendarMonth: Int, calendarDay: Int) {
        var y = calendarYear
        var m = calendarMonth
        var d = calendarDay
        val datePickerDialog =
            DatePickerDialog(inflater.context, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val dateString = dayOfMonth.toString() + "/" + month.plus(1) + "/" + year
                edtTextInput.setText(dateString)
                y = year
                m = month
                d = dayOfMonth
            }, y, m, d)
        datePickerDialog.show()
    }

}