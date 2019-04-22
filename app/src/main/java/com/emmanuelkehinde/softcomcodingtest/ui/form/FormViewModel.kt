package com.emmanuelkehinde.softcomcodingtest.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emmanuelkehinde.softcomcodingtest.data.model.Form
import com.emmanuelkehinde.softcomcodingtest.data.model.Value
import javax.inject.Inject

class FormViewModel @Inject constructor(): ViewModel() {

    var form: Form? = null

    private val _pageIndex = MutableLiveData<Int>().apply { value = 0 }
    val pageIndex : LiveData<Int>
        get() = _pageIndex

    var textInputs: MutableMap<String, String> = mutableMapOf()
    var radioInputs: MutableMap<String, Value> = mutableMapOf()

    val isFirstPage: Boolean
        get() = _pageIndex.value == 0

    val isLastPage: Boolean
        get() = _pageIndex.value == form?.pages?.lastIndex

    fun goToNextPage() {
        if (hasNext()) {
            _pageIndex.value = _pageIndex.value!! + 1
        }
    }

    fun goToPreviousPage() {
        if (hasPrevious()) {
            _pageIndex.value = _pageIndex.value!! - 1
        }
    }

    private fun hasNext(): Boolean {
        return form?.pages?.let {
            it.lastIndex > _pageIndex.value!!
        } ?: false

    }

    private fun hasPrevious(): Boolean {
        return _pageIndex.value!! - 1 > -1
    }

    fun getProgress(): Int {
        form?.pages?.size?.let { totalPageSize ->
            return ((_pageIndex.value!! + 1) * 100)/totalPageSize
        }
        return 0
    }
}