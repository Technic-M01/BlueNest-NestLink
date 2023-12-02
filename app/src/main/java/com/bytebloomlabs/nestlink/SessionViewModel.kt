package com.bytebloomlabs.nestlink

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SessionViewModel: ViewModel() {

    private val _triggerAuth = MutableLiveData<Boolean>().apply { value = false }
    val triggerAuth: LiveData<Boolean> get() = _triggerAuth

    fun setTriggerAuth(trigger: Boolean) {
        _triggerAuth.value = trigger
    }

}