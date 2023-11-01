package com.example.bdandsubd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {
    private val callbackLiveData = MutableLiveData<Boolean>()

    fun getCallbackLiveData() = callbackLiveData

    fun setCallbackValue(value: Boolean) {
        callbackLiveData.value = value
    }
}