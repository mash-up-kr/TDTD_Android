package com.tdtd.presentation.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.tdtd.presentation.R
import kotlinx.android.synthetic.main.layout_toast.view.*
import java.util.concurrent.TimeUnit
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

fun initParentHeight(activity: Activity, view: View?, sub: Int) {
    val displayMetrics = DisplayMetrics()

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        val display = activity.display
        display?.getRealMetrics(displayMetrics)
    } else {
        val display = activity.windowManager.defaultDisplay
        display.getMetrics(displayMetrics)
    }

    val deviceHeight: Int = displayMetrics.heightPixels
    val layoutParams = view?.layoutParams
    layoutParams?.height = deviceHeight - sub.toPx()

    view?.layoutParams = layoutParams
}

fun setupFullHeight(bottomSheet: View) {
    val layoutParams = bottomSheet.layoutParams
    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT

    bottomSheet.layoutParams = layoutParams
}

fun getBottomNavigationBarHeight(view: View): Int {
    var bottomBarHeight = 0
    val resourceIdBottom: Int =
        view.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceIdBottom > 0) bottomBarHeight =
        view.resources.getDimensionPixelSize(resourceIdBottom)

    return bottomBarHeight
}

fun dpToPx(view: View, dp: Int): Int {
    val dense = view.resources.displayMetrics.density
    return (dp * dense).roundToInt()
}

fun randomAngle(): String {
    val array = arrayOf(
        "-10",
        "-9",
        "-8",
        "-7",
        "-6",
        "-5",
        "-4",
        "-3",
        "-2",
        "-1",
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10"
    )
    return array.random()
}

fun playerFormat(duration: Long, endTime: TextView) {
    val format = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(
        TimeUnit.MILLISECONDS.toMinutes(duration)
    )
    if (format < 10) {
        endTime.text = String.format("00:0%d", format)
    } else {
        endTime.text = String.format("00:%d", format)
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.onThrottleClick(action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(listener))
}

fun View.clickWithDebounce(debounceTime: Long = 300, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            v.isEnabled = false
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)

    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(resId, args, navOptions, navExtras)
    }
}
