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
import kotlin.math.roundToInt

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

fun initParentHeight(activity: Activity, view: View?, sub : Int) {
    val displayMetrics = DisplayMetrics()

    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        val display = activity.display
        display?.getRealMetrics(displayMetrics)
    }else {
        val display = activity.windowManager.defaultDisplay
        display.getMetrics(displayMetrics)
    }

    val deviceHeight: Int = displayMetrics.heightPixels
    val layoutParams = view?.layoutParams
    layoutParams?.height = deviceHeight - sub.toPx()

    view?.layoutParams = layoutParams
}

fun getBottomNavigationBarHeight(view: View): Int {
    var bottomBarHeight = 0
    val resourceIdBottom: Int = view.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceIdBottom > 0) bottomBarHeight = view.resources.getDimensionPixelSize(resourceIdBottom)

    return bottomBarHeight
}

fun dpToPx(view: View, dp: Int): Int {
    val dense = view.resources.displayMetrics.density
    return (dp * dense).roundToInt()
}