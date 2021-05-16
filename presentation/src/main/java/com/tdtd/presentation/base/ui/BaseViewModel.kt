package com.tdtd.presentation.base.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()

    init {
        isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()

        hideLoading()
    }

    fun showLoading() {
        isLoading.value = true
    }

    fun hideLoading() {
        isLoading.value = false
    }
}