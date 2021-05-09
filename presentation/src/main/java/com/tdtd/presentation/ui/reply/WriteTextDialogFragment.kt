package com.tdtd.presentation.ui.reply

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.domain.entity.StickerColorType
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.FragmentWriteTextBinding
import com.tdtd.presentation.ui.detail.DetailViewModel
import com.tdtd.presentation.util.*
import com.tdtd.presentation.util.MultiPartForm.getBody
import okhttp3.MultipartBody


class WriteTextDialogFragment : BottomSheetDialogFragment() {

    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var binding: FragmentWriteTextBinding
    private val safeArgs: WriteTextDialogFragmentArgs by navArgs()
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
            behavior.peekHeight = 0
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        behavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheetPadding(view)
        initParentHeight(requireActivity(), view, 24)
        setNickNameEditFocus()
        setWriteTextEditFocus()
        setTextWatcher()
        onClickCancelButton()
    }

    private fun setNickNameEditFocus() {
        binding.nicknameEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                binding.nicknameEditText.hint = null
            } else {
                binding.nicknameEditText.hint = getString(R.string.record_voice_title_hint)
                view.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
            }
        }
    }

    private fun setWriteTextEditFocus() {
        binding.writeTextEditView.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                binding.writeTextEditView.hint = null
            } else {
                view.setBackgroundResource(R.drawable.background_beige2_radius16)
                binding.writeTextEditView.hint = getString(R.string.record_voice_whisper_title)
            }
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
                        getString(R.string.record_voice_nickname_number, s?.length)
                    writeTextEditView.setBackgroundResource(R.drawable.background_beige2_radius16)
                }
            }

            if (binding.writeTextEditView.hasFocus()) {
                binding.apply {
                    contentText = s.toString()
                    writeTextEditView.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                    currentContentTextView.text =
                        getString(R.string.initial_and_max_content_number, s?.length)
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
        val part: ArrayList<MultipartBody.Part> = ArrayList()
        part.add(getBody("nickname", nickNameText))
        part.add(getBody("message_type", "TEXT"))
        part.add(getBody("text_message", contentText))
        part.add(getBody("sticker_color", StickerColorType.values().random().toString()))
        part.add(getBody("sticker_angle", randomAngle()))

        binding.apply {
            completeButton.isEnabled = true
            completeButton.setBackgroundResource(R.drawable.backgroud_grayscale1_radius12_click)

            completeButton.setOnClickListener {
                detailViewModel.postReplyUserComment(
                    safeArgs.roomCode,
                    part
                ).also {
                    setNavigationResult("success", "comment")
                    dismiss()
                }
            }
        }
    }

    private fun setBottomSheetPadding(view: View) {
        if (getBottomNavigationBarHeight(view) < Constants.BOTTOM_NAVIGATION_HEIGHT) {
            binding.writeTextBottomSheet.setPadding(
                dpToPx(view, 16),
                dpToPx(view, 16),
                dpToPx(view, 24),
                dpToPx(view, 32)
            )
        }
    }
}