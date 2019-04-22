package com.emmanuelkehinde.softcomcodingtest

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.emmanuelkehinde.softcomcodingtest.ui.custom.FormTextInputLayout
import com.google.android.material.textfield.TextInputEditText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class FormTextInputLayoutTest {

    lateinit var formTextInputLayout: FormTextInputLayout
    lateinit var edtTextInput: TextInputEditText
    private lateinit var activity: Context
    private val testText = "Test Text"

    @Before
    fun setup(){
        activity = Robolectric.buildActivity(FragmentActivity::class.java)
            .create()
            .resume()
            .get()

        edtTextInput = TextInputEditText(activity)
        edtTextInput.id = R.id.edt_text_input

        formTextInputLayout = mock(FormTextInputLayout::class.java)
        formTextInputLayout.isMandatory = true
        formTextInputLayout.addView(edtTextInput)
    }

    @Test
    fun testEditTextInputText_isSet() {
        edtTextInput.setText(testText)
        assertEquals(testText, edtTextInput.text.toString())
    }

    @Test
    fun testValidateFunctionWorks() {
        assertFalse(formTextInputLayout.validate())
    }

}