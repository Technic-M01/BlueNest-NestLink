package com.bytebloomlabs.nestlink.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bytebloomlabs.nestlink.utils.Event
import com.bytebloomlabs.nestlink.utils.ToastEvent
import com.bytebloomlabs.nestlink.utils.ToastIcons
import com.bytebloomlabs.nestlink.utils.ToastSeverity

class SessionViewModel: ViewModel() {
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