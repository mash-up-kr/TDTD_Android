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
        observeNickNameEditChange()
        observeWriteTextEditChange()
        onClickCancelButton()
        setCompleteButton()
    }

    private fun setNickNameEditFocus() {
        binding.completeButton.isEnabled = false

        binding.nicknameEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) view.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
            else view.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
        }
    }

    private fun observeNickNameEditChange() {
        binding.currentTextLengthTextView.text = getString(R.string.recode_voice_nickname_number, 0)

        binding.nicknameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.apply {
                    nicknameEditText.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                    currentTextLengthTextView.text =
                        getString(R.string.recode_voice_nickname_number, s?.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun observeWriteTextEditChange() {
        binding.writeTextEditView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.apply {
                    writeTextEditView.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                }
            }
        })
    }

    private fun onClickCancelButton() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setCompleteButton() {
        if (binding.writeTextEditView.text?.isNotEmpty() == true) {
            binding.completeButton.apply {
                isEnabled = true
                setBackgroundResource(R.drawable.backgroud_grayscale1_radius12_click)
            }
        }
    }
}