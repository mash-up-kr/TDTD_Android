package com.tdtd.presentation.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.tdtd.presentation.R
import kotlinx.android.synthetic.main.layout_toast.view.*

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Context.showToast(text: String, view: View) {
    val popupView = LayoutInflater.from(this).inflate(R.layout.layout_toast, null).apply {
        toastTextView.text = text
    }

    val popupWindow = PopupWindow(
        popupView,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    popupWindow.apply {
        showAtLocation(view, Gravity.TOP or Gravity.CENTER, 0, 0)
        elevation = 8f
        animationStyle = R.style.PopupWindowToast
        Handler(Looper.getMainLooper()).postDelayed({
            popupWindow.dismiss()
        }, 2000)
    }
}

fun initParentHeight(activity: Activity, view: View?) {
    val displayMetrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(displayMetrics)

    val deviceHeight: Int = displayMetrics.heightPixels
    val layoutParams = view?.layoutParams
    layoutParams?.height = deviceHeight - 24.toPx()

    view?.layoutParams = layoutParams
}