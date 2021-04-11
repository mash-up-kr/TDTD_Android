package com.tdtd.presentation.ui.writetext

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.FragmentWriteTextBinding
import com.tdtd.presentation.util.initParentHeight

class WriteTextDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentWriteTextBinding
    private var nickNameText = ""
    private var contentText = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_text, container, false)
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
        setNickNameEditFocus()
        setWriteTextEditFocus()
        setTextWatcher()
        onClickCancelButton()
    }

    private fun setNickNameEditFocus() {
        binding.nicknameEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) view.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
            else view.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
        }
    }

    private fun setWriteTextEditFocus() {
        binding.writeTextEditView.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) view.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
            else view.setBackgroundResource(R.drawable.background_beige2_radius16)
        }
    }

    private fun setTextWatcher() {
        binding.apply {
            nicknameEditText.addTextChangedListener(textWatcher)
            writeTextEditView.addTextChangedListener(textWatcher)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (binding.nicknameEditText.hasFocus()) {
                binding.apply {
                    nickNameText = s.toString()
                    nicknameEditText.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                    currentTextLengthTextView.text =
                        getString(R.string.recode_voice_nickname_number, s?.length)
                    writeTextEditView.setBackgroundResource(R.drawable.background_beige2_radius16)
                }
            }
            if (binding.writeTextEditView.hasFocus()) {
                binding.apply {
                    contentText = s.toString()
                    writeTextEditView.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                    nicknameEditText.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
                }
            }
            if (nickNameText.isNotEmpty() && contentText.isNotEmpty()) setCompleteButton()
            else emptyEditView()
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    private fun onClickCancelButton() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun emptyEditView() {
        binding.apply {
            completeButton.isEnabled = false
            completeButton.setBackgroundResource(R.drawable.background_grayscale1_radius12)
        }
    }

    private fun setCompleteButton() {
        binding.apply {
            completeButton.isEnabled = true
            completeButton.setBackgroundResource(R.drawable.backgroud_grayscale1_radius12_click)
        }
    }
}