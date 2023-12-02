package com.bytebloomlabs.nestlink

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bytebloomlabs.nestlink.utils.Event
import com.bytebloomlabs.nestlink.utils.ToastEvent
import com.bytebloomlabs.nestlink.utils.ToastIcons
import com.bytebloomlabs.nestlink.utils.ToastSeverity

class SessionViewModel: ViewModel() {

    private val _triggerAuth = MutableLiveData<Boolean>().apply { value = false }
    val triggerAuth: LiveData<Boolean> get() = _triggerAuth

    fun setTriggerAuth(trigger: Boolean) {
        _triggerAuth.value = trigger
    }

    /* live data for showing custom toast messages */
    private val _message = MutableLiveData<Event<ToastEvent>>()
    val message: LiveData<Event<ToastEvent>> get() = _message

    fun createCustomToast(
        message: String,
        icon: ToastIcons? = null,
        severity: ToastSeverity? = null
    ) {
        _message.value = Event(ToastEvent(message, icon, severity))
    }


}