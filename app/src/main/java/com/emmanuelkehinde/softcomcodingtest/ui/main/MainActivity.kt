package com.emmanuelkehinde.softcomcodingtest.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.emmanuelkehinde.softcomcodingtest.App
import com.emmanuelkehinde.softcomcodingtest.R
import com.emmanuelkehinde.softcomcodingtest.data.EXTRA_FORM
import com.emmanuelkehinde.softcomcodingtest.data.FAILED_TO_LOAD_FORM
import com.emmanuelkehinde.softcomcodingtest.data.model.Form
import com.emmanuelkehinde.softcomcodingtest.ui.form.FormActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.getInstance().getAppComponent().inject(this)

        val formViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        formViewModel.form?.observe(this, Observer<Form>{form ->
            updateView(form)
        })

        btn_proceed.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java).apply {
                putExtra(EXTRA_FORM, formViewModel.form?.value)
            }).also { finish() }
        }

    }

    private fun updateView(form: Form?) {
        if (form != null) {
            tv_form_name.text = form.name
            btn_proceed.isEnabled = true
        } else {
            tv_instruction.text = FAILED_TO_LOAD_FORM
        }
    }
}
