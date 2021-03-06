package com.tdtd.presentation.ui.recordvoice

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
import kotlinx.android.synthetic.main.detail_voice_bottom_sheet.*

class RecordVoiceDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_voice_bottom_sheet, container, false)
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
        nicknameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) nicknameEditText.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
            else nicknameEditText.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
        }
    }

    private fun setRoomEditView() {
        currentTextLengthTextView.text = getString(R.string.recode_voice_nickname_number, 0)

        nicknameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nicknameEditText.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                currentTextLengthTextView.text = getString(R.string.recode_voice_nickname_number, s?.length)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun setRollingPager() {
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
