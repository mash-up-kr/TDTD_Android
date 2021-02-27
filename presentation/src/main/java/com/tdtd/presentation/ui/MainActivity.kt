package com.tdtd.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.tdtd.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRoomDialogFragment()
    }

    private fun initRoomDialogFragment() {
        val bottomSheet = RoomDialogFragment()
        bottomSheet.run {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
            show(supportFragmentManager, bottomSheet.tag)
        }
    }
}