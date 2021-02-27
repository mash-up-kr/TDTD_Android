package com.tdtd.presentation.ui

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.util.toPx
import kotlinx.android.synthetic.main.room_bottom_sheet.*

class RoomDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.room_bottom_sheet, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initParentHeight()
        setRoomEditFocus()
        setRoomEditView()
        setRollingPager()
    }

    private fun setRoomEditFocus() {
        RoomNameEditView.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) RoomNameEditView.setBackgroundResource(R.drawable.edit_room_click_border)
            else RoomNameEditView.setBackgroundResource(R.drawable.edit_room_border)
        }
    }

    private fun setRoomEditView() {
        RoomNameEditView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                RoomNameEditView.setBackgroundResource(R.drawable.edit_room_click_border)
                InputNumberTextView.text = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                InputNumberTextView.text = s?.length.toString()
            }
        })
    }

    private fun setRollingPager() {
        VoiceImageView.setOnClickListener {
            //VoiceImageView.setImageResource(R.drawable.edit_room_click_border)
        }
        TextImageView.setOnClickListener {
            //TextImageView.setImageResource(R.drawable.edit_room_click_border)
        }
    }

    private fun initParentHeight() {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        val deviceHeight: Int = displayMetrics.heightPixels
        val layoutParams = view?.layoutParams
        layoutParams?.height = deviceHeight - 24.toPx()

        view?.layoutParams = layoutParams
    }
}