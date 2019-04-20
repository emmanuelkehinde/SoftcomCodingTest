package com.emmanuelkehinde.softcomcodingtest.ui.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emmanuelkehinde.softcomcodingtest.data.model.Form
import javax.inject.Inject

class FormViewModel @Inject constructor(): ViewModel() {

    var form: MutableLiveData<Form>? = MutableLiveData()

    private val _pageIndex = MutableLiveData<Int>().apply { value = 0 }
    val pageIndex : LiveData<Int>
        get() = _pageIndex

    val isFirstPage: Boolean
        get() = _pageIndex.value == 0

    val isLastPage: Boolean
        get() = _pageIndex.value == form?.value?.pages?.lastIndex

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
        return form?.value?.pages?.let {
            it.lastIndex > _pageIndex.value!!
        } ?: false

    }

    private fun hasPrevious(): Boolean {
        return _pageIndex.value!! - 1 > -1
    }

    fun getProgress(): Int {
        form?.value?.pages?.size?.let { totalPageSize ->
            return ((_pageIndex.value!! + 1) * 100)/totalPageSize
        }
        return 0
    }
}