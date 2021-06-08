package com.tdtd.presentation.util

import android.view.View

class OnThrottleClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 300
) : View.OnClickListener {

    private var clickable = true

    override fun onClick(v: View?) {
        if (clickable) {
            clickable = false
            v?.run {
                postDelayed({
                    clickable = true
                }, interval)
                clickListener.onClick(v)
            }
        }
    }
}