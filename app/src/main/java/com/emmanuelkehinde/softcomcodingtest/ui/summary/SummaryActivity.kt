package com.emmanuelkehinde.softcomcodingtest.ui.summary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emmanuelkehinde.softcomcodingtest.R
import com.emmanuelkehinde.softcomcodingtest.data.EXTRA_FORM
import com.emmanuelkehinde.softcomcodingtest.data.model.Form
import com.emmanuelkehinde.softcomcodingtest.ui.form.FormActivity
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val form: Form? = intent?.extras?.getParcelable(EXTRA_FORM)
        tv_form_name.text = form?.name

        btn_finish.setOnClickListener { finish() }
        btn_start_again.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java).apply {
                putExtra(EXTRA_FORM, form)
            })
            finish()
        }
    }
}
