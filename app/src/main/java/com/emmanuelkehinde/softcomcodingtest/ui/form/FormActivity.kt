package com.emmanuelkehinde.softcomcodingtest.ui.form

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.emmanuelkehinde.shutdown.Shutdown
import com.emmanuelkehinde.softcomcodingtest.App
import com.emmanuelkehinde.softcomcodingtest.R
import com.emmanuelkehinde.softcomcodingtest.data.EXTRA_FORM
import com.emmanuelkehinde.softcomcodingtest.data.SELECT_AN_OPTION
import com.emmanuelkehinde.softcomcodingtest.data.model.Action
import com.emmanuelkehinde.softcomcodingtest.data.model.Form
import com.emmanuelkehinde.softcomcodingtest.data.model.Page
import com.emmanuelkehinde.softcomcodingtest.data.model.Value
import com.emmanuelkehinde.softcomcodingtest.extension.showConfirmDialog
import com.emmanuelkehinde.softcomcodingtest.extension.showToast
import com.emmanuelkehinde.softcomcodingtest.ui.custom.FormRadioGroup
import com.emmanuelkehinde.softcomcodingtest.ui.custom.FormTextInputLayout
import com.emmanuelkehinde.softcomcodingtest.ui.summary.SummaryActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.*
import kotlin.concurrent.schedule


class FormActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var formViewModel: FormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        App.getInstance().getAppComponent().inject(this)

        formViewModel = ViewModelProviders.of(this, viewModelFactory).get(FormViewModel::class.java)
                .apply {
                    intent?.extras?.let { intent ->
                        val form: Form? = intent.getParcelable(EXTRA_FORM)
                        form?.let { fm ->
                            this.form?.value = fm
                        }
                    }
                }

        formViewModel.pageIndex.observe(this, Observer { pageIndex ->
            updateViews(formViewModel.form?.value?.pages?.get(pageIndex))
        })

        btn_next_page.setOnClickListener {
            if (!validatePage()) return@setOnClickListener
            saveUserInputs()
            formViewModel.goToNextPage()
        }

        btn_previous_page.setOnClickListener {
            saveUserInputs()
            formViewModel.goToPreviousPage()
        }

        btn_submit.setOnClickListener {
            if (!validatePage()) return@setOnClickListener
            saveUserInputs()

            showConfirmDialog("Yes", {
                startActivity(Intent(this, SummaryActivity::class.java).apply {
                    putExtra(EXTRA_FORM, formViewModel.form?.value)
                }).also { finish() }
            }, "Cancel", msg = "Proceed to submit?")
        }

    }

    private fun updateViews(page: Page?) {
        if (page == null) return
        updateFormHeader(page)
        updateFormBody(page)
        updateFormFooter()
    }

    private fun updateFormHeader(page: Page) {
        tv_page_title.text = page.label
    }

    private fun updateFormBody(page: Page) {
        page_layout.removeAllViewsInLayout() //Remove previous views

        val totalViews = ArrayList<View>()
        page.sections.forEach { section->
            totalViews.addAll(FormInteractor.getInstance().getFormElements(layoutInflater, page_layout, section))
        }

        totalViews.forEach { view->

            if (view is FormTextInputLayout) {
                val input: String? = formViewModel.textInputs[view.tag.toString()]
                val edtTextInput = view.findViewById<TextInputEditText>(R.id.edt_text_input)
                input?.let {
                    edtTextInput.setText(it)
                }
            }

            if (view is FormRadioGroup) {
                view.setOnCheckedChangeListener(onCheckedChangeListener(view))

                GlobalScope.launch(context = Dispatchers.Main) {
                    delay(50) //Slightly wait for other page views to be added

                    val input: Value? = formViewModel.radioInputs[view.tag.toString()]
                    val rbYes = view.findViewById(R.id.rb_yes) as RadioButton
                    val rbNo = view.findViewById(R.id.rb_no) as RadioButton
                    input?.let {
                        if(it == Value.YES) {
                            rbYes.isChecked = true
                        } else rbNo.isChecked = true
                    }
                }

            }

            page_layout.addView(view)
        }
    }

    private fun updateFormFooter() {
        progress_bar.progress = formViewModel.getProgress()

        when {
            formViewModel.isFirstPage -> { //First Page
                btn_previous_page.visibility = View.GONE
                btn_next_page.visibility = View.VISIBLE
                btn_submit.visibility = View.GONE
            }
            formViewModel.isLastPage -> { //Last Page
                btn_previous_page.visibility = View.VISIBLE
                btn_next_page.visibility = View.GONE
                btn_submit.visibility = View.VISIBLE
            }
            else -> { //Other pages
                btn_previous_page.visibility = View.VISIBLE
                btn_next_page.visibility = View.VISIBLE
                btn_submit.visibility = View.GONE
            }
        }
    }

    private fun onCheckedChangeListener(view: FormRadioGroup): RadioGroup.OnCheckedChangeListener {
        return RadioGroup.OnCheckedChangeListener { _, checkedId ->
            view.rules.forEach { rule ->
                if (rule.action == Action.SHOW && checkedId == R.id.rb_yes) {
                    rule.targets.forEach { target ->
                        page_layout.findViewWithTag<View>(target).visibility = View.VISIBLE
                    }
                } else {
                    rule.targets.forEach { target ->
                        page_layout.findViewWithTag<View>(target).visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun validatePage(): Boolean {
        page_layout.children.forEach { view->
            when (view) {
                is FormTextInputLayout -> {
                    if (!view.validate()) {
                        return false
                    }
                }
                is FormRadioGroup -> {
                    if (!view.validate()) {
                        showToast(SELECT_AN_OPTION)
                        return false
                    }
                }
            }
        }
        return true
    }


    private fun saveUserInputs() {
        page_layout.children.forEach { view->
            when (view) {
                is FormTextInputLayout -> {
                    val edtTextInput = view.findViewById<TextInputEditText>(R.id.edt_text_input)
                    formViewModel.textInputs[view.tag.toString()] = edtTextInput.text.toString()
                }
                is FormRadioGroup -> {
                    val rbYes = view.findViewById(R.id.rb_yes) as RadioButton
                    formViewModel.radioInputs[view.tag.toString()] = if(rbYes.isChecked) Value.YES else Value.NO
                }
            }
        }
    }

    override fun onBackPressed() {
        Shutdown.now(this)
    }
}
