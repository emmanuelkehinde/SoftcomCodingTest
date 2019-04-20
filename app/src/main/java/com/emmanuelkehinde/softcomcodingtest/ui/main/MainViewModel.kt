package com.emmanuelkehinde.softcomcodingtest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emmanuelkehinde.softcomcodingtest.data.repo.FormRepository
import com.emmanuelkehinde.softcomcodingtest.data.model.Form
import javax.inject.Inject

class MainViewModel @Inject constructor(formRepository: FormRepository) : ViewModel() {

    private var _form: MutableLiveData<Form>? = MutableLiveData()

    init {
        if (_form?.value == null) {
            _form?.value = formRepository.getForm()
        }
    }

    val form: LiveData<Form>?
        get() = _form
}