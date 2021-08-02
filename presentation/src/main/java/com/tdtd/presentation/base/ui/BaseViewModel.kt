package com.tdtd.presentation.base.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tdtd.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _apiFailEvent = SingleLiveEvent<Boolean>()
    val apiFailEvent: LiveData<Boolean> get() = _apiFailEvent

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            _apiFailEvent.postCall()
        }

    fun CoroutineScope.safeLaunch(
        exceptionHandler: CoroutineExceptionHandler = coroutineExceptionHandler,
        launchBody: suspend () -> Unit
    ): Job {
        return this.launch(exceptionHandler) {
            launchBody.invoke()
        }
    }
}