package com.example.bdandsubd.presenter.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedRoomViewModel:ViewModel() {
    private val callbackLiveData = MutableLiveData<Boolean>()

    fun getCallbackLiveData() = callbackLiveData

    fun setCallbackValue(value: Boolean) {
        callbackLiveData.value = value
    }
}