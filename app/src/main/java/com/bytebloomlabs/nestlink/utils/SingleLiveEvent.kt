package com.bytebloomlabs.nestlink.utils

/**
 * Event wrapper class with mutable live data
 * */
open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set // allow an external read not write


    /* returns the content and prevents its use again */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /* returns the content, even if it's already been handled */
    fun peekContent(): T = content
}

data class ToastEvent(val message: String, val icon: ToastIcons?, val severity: ToastSeverity?)