package com.tdtd.presentation.ui.makeroom

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.RoomBottomSheetBinding
import com.tdtd.presentation.ui.recordvoice.RecordVoiceDialogFragment
import com.tdtd.presentation.ui.writetext.WriteTextDialogFragment
import com.tdtd.presentation.util.initParentHeight

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

        initParentHeight(requireActivity(), view)
        setRoomEditFocus()
        setRoomEditView()
        onClickVoice()
        onClickText()
    }

    private fun setRoomEditFocus() {
        binding.makeRoomButton.isEnabled = false

        binding.roomNameEditView.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) view.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
            else view.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
        }
    }

    private fun setRoomEditView() {
        binding.roomNameEditView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.apply {
                    roomNameEditView.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
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
            it.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
            binding.textImageView.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
            binding.makeRoomButton.apply {
                isEnabled = true
                setBackgroundResource(R.drawable.backgroud_grayscale1_radius12_click)
                setOnClickListener {
                    showRecordVoiceDialogFragment()
                }
            }
        }
    }

    private fun onClickText() {
        binding.textImageView.setOnClickListener {
            it.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
            binding.voiceImageView.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
            binding.makeRoomButton.apply {
                isEnabled = true
                setBackgroundResource(R.drawable.backgroud_grayscale1_radius12_click)
                setOnClickListener {
                    showWriteTextDialogFragment()
                }
            }
        }
    }

    private fun showRecordVoiceDialogFragment() {
        val bottomSheet = RecordVoiceDialogFragment()

        bottomSheet.also {
            bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun showWriteTextDialogFragment() {
        val bottomSheet = WriteTextDialogFragment()

        bottomSheet.also {
            bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }
}
