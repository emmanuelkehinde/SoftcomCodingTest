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
import com.emmanuelkehinde.softcomcodingtest.data.model.Element
import com.emmanuelkehinde.softcomcodingtest.data.model.ElementType
import com.emmanuelkehinde.softcomcodingtest.ui.custom.FormTextInputLayout
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class EditTextWidget: FormWidget {

    override fun getView(parent: ViewGroup, inflater: LayoutInflater, element: Element): View {
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
                edtTextInput.addTextChangedListener(onTextChangedListener(edtTextInput))
            }
            element.type == ElementType.DATE_TIME -> {
                edtTextInput.isFocusableInTouchMode = false
                edtTextInput.setOnClickListener {
                    showDatePickerDialog(inflater, edtTextInput, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH)
                }
            }
        }

        return editText
    }

    private fun addLengthFilter(edtTextInput: TextInputEditText) {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(13)
        edtTextInput.filters = filterArray
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


    private fun onTextChangedListener(edtTextInput: TextInputEditText): TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            edtTextInput.removeTextChangedListener(this)

            try {
                var originalString = s.toString()

//                val longVal: Long?
//                if (originalString.contains("-")) {
//                    originalString = originalString.replace("-".toRegex(), "")
//                }
//                longVal = originalString.toLong()



//                val sb = StringBuilder()
//                var tmp = longVal
//                sb.append("")
//                sb.append(tmp / 10000000)
//                tmp %= 10000000
//                sb.append("-")
//                sb.append(tmp / 10000)
//                tmp %= 10000000
//                sb.append("-")
//                sb.append(tmp)

//                val formatter = NumberFormat.getInstance(Locale.getDefault()) as DecimalFormat
//                formatter.applyPattern("#-###-###-###")
//                val formattedString = formatter.format(longVal)

                edtTextInput.setText(originalString.replace("([0-9]{4})([0-9]{3})([0-9]{4})", "$1-$2-$3"))
                edtTextInput.setSelection(edtTextInput.text?.length ?: 0)
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }


            edtTextInput.addTextChangedListener(this)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }

}