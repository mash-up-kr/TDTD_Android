package com.tdtd.presentation.ui.makeroom

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.RoomBottomSheetBinding
import com.tdtd.presentation.util.toPx

class RoomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: RoomBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.room_bottom_sheet, container, false)
        binding.lifecycleOwner = this
        return binding.root
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
        onClickVoice()
        onClickText()
        onClickMakeRoomButton()
    }

    private fun setRoomEditFocus() {
        binding.roomNameEditView.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) view.setBackgroundResource(R.drawable.edit_room_click_border)
            else view.setBackgroundResource(R.drawable.edit_room_border)
        }
    }

    private fun setRoomEditView() {
        binding.roomNameEditView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.apply {
                    roomNameEditView.setBackgroundResource(R.drawable.edit_room_click_border)
                    textNumberTextView.text =
                        getString(R.string.initial_and_max_input_number, s?.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun onClickVoice() {
        binding.voiceImageView.setOnClickListener {
            it.setBackgroundResource(R.drawable.edit_room_click_border)
            // 음성 작성 페이지
        }
    }

    private fun onClickText() {
        binding.textImageView.setOnClickListener {
            it.setBackgroundResource(R.drawable.edit_room_click_border)
            // 텍스트 작성 페이지
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

    private fun onClickMakeRoomButton() {
        binding.makeRoomButton.setOnClickListener {
            it.setBackgroundResource(R.drawable.backgroud_grayscale1_radius12_click)
        }
    }
}
