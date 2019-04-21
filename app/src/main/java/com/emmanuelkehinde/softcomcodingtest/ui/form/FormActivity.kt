package com.emmanuelkehinde.softcomcodingtest.ui.form

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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
import com.emmanuelkehinde.softcomcodingtest.extension.showToast
import com.emmanuelkehinde.softcomcodingtest.ui.custom.FormRadioGroup
import com.emmanuelkehinde.softcomcodingtest.ui.custom.FormTextInputLayout
import com.emmanuelkehinde.softcomcodingtest.ui.summary.SummaryActivity
import kotlinx.android.synthetic.main.activity_form.*
import javax.inject.Inject

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
            formViewModel.goToNextPage()
        }

        btn_previous_page.setOnClickListener {
            formViewModel.goToPreviousPage()
        }

        btn_submit.setOnClickListener {
            if (!validatePage()) return@setOnClickListener
            startActivity(Intent(this, SummaryActivity::class.java).apply {
                putExtra(EXTRA_FORM, formViewModel.form?.value)
            }).also { finish() }
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
            page_layout.addView(view)

            if (view is FormRadioGroup) {
                view.setOnCheckedChangeListener(onCheckedChangeListener(view, page_layout))
            }
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

    private fun onCheckedChangeListener(view: FormRadioGroup, pageLayout: LinearLayout): RadioGroup.OnCheckedChangeListener {
        return RadioGroup.OnCheckedChangeListener { _, checkedId ->
            view.rules.forEach { rule ->
                if (rule.action == Action.SHOW && checkedId == R.id.rb_yes) {
                    rule.targets.forEach { target ->
                        pageLayout.findViewWithTag<View>(target).visibility = View.VISIBLE
                    }
                } else {
                    rule.targets.forEach { target ->
                        pageLayout.findViewWithTag<View>(target).visibility = View.INVISIBLE
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

    override fun onBackPressed() {
        Shutdown.now(this)
    }
}
