package com.bytebloomlabs.nestlink.utils

import android.app.Activity
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bytebloomlabs.nestlink.MainActivity
import com.bytebloomlabs.nestlink.R
import com.bytebloomlabs.nestlink.fragments.AuthDialogFragment

internal fun Activity.showSignupDialog() {
    val fragmentManager = (this as MainActivity).supportFragmentManager
    val newFragment = AuthDialogFragment()

    val transaction = fragmentManager.beginTransaction()
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

    transaction
        .add(android.R.id.content, newFragment)
        .addToBackStack(null)
        .commit()
}



internal fun Activity.showCustomToast(
    message: String,
    icon: ToastIcons? = null,
    severity: ToastSeverity? = null
) {
    Toast(this).customToast(message, icon, severity, this)
}


internal fun Fragment.showCustomToast(
    message: String,
    icon: ToastIcons? = null,
    severity: ToastSeverity? = null
) {
    Toast(requireContext()).customToast(message, icon, severity, requireActivity())
}


internal fun Toast.customToast(
    message: String,
    icon: ToastIcons? = null,
    severity: ToastSeverity? = null,
    activity: Activity) {

    val layout = activity.layoutInflater.inflate(R.layout.custom_toast, activity.findViewById(R.id.toast_container))

    val messageTv = layout.findViewById<TextView>(R.id.tv_custom_toast_message)
    messageTv.text = message

    val iconIv = layout.findViewById<ImageView>(R.id.iv_custom_toast_icon)
    if (icon != null) {
        iconIv.setImageResource(icon.resId)
    }

    if (severity != null) {
        DrawableCompat.setTint(iconIv.drawable, ContextCompat.getColor(activity, severity.resId))
    }

    this.apply {
        setGravity(Gravity.BOTTOM, 0, 40)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}

enum class ToastIcons(val resId: Int) {
//    NoAccount(R.drawable.ic_no_account),
//    Account(R.drawable.ic_account),
    Info(R.drawable.ic_info)
//    Refresh(R.drawable.ic_refresh),
//    Write(R.drawable.ic_edit),
//    Warning(R.drawable.ic_warning_triangle),
//    Locked(R.drawable.ic_device_lock)
}

enum class ToastSeverity(val resId: Int) {
    Info(R.color.blue_main),
    Success(R.color.green_main),
    Warning(R.color.yellow_main),
    Error(R.color.red_main)
}