package com.tdtd.presentation.ui.reply

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.domain.entity.StickerColorType
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.FragmentRecordVoiceBinding
import com.tdtd.presentation.ui.detail.DetailViewModel
import com.tdtd.presentation.utils.*
import com.tdtd.presentation.utils.AdsExt.showAds
import com.tdtd.presentation.utils.Constants.REQUEST_RECORD_AUDIO_PERMISSION
import com.tdtd.presentation.utils.Constants.STATE_NORMAL
import com.tdtd.presentation.utils.Constants.STATE_PAUSE
import com.tdtd.presentation.utils.Constants.STATE_PLAYING
import com.tdtd.presentation.utils.Constants.STATE_RECORD
import com.tdtd.presentation.utils.Constants.STATE_RECORD_STOP
import com.tdtd.presentation.utils.MultiPartForm.getAudioBody
import com.tdtd.presentation.utils.MultiPartForm.getBody
import okhttp3.MultipartBody
import java.io.File


class RecordVoiceDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRecordVoiceBinding
    private val detailViewModel: DetailViewModel by activityViewModels()
    private val safeArgs: RecordVoiceDialogFragmentArgs by navArgs()
    private var file: String = ""
    private var currentState = STATE_NORMAL
    private var isPlaying = false
    private var isRecord = false
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var nickNameText = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_record_voice, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                setupFullHeight(sheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeKeyboard()
        AdsExt.initAds(requireContext())
        initRecord()
        onClickCancelButton()
        setNickNameEditFocus()
        observeNickNameEditChange()
    }

    override fun onResume() {
        super.onResume()
        onClickRecord()
    }

    private fun observeKeyboard() {
        binding.recordVoiceBottomSheet.setOnClickListener { it.hideKeyboard() }
    }

    private fun initRecord() {
        file = "${requireActivity().externalCacheDir?.absolutePath}/recording.mp3"
        MediaRecorderHelper.fileName = file

        ActivityCompat.requestPermissions(
            requireActivity(),
            permissions,
            REQUEST_RECORD_AUDIO_PERMISSION
        )
    }

    private fun setNickNameEditFocus() {
        binding.nicknameEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.nicknameEditText.hint = null
                view.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
            } else {
                binding.nicknameEditText.hint = getString(R.string.room_name_hint_title)
                view.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
                view.hideKeyboard()
            }
        }
    }

    private fun observeNickNameEditChange() {
        binding.nicknameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.apply {
                    nickNameText = s.toString()
                    nicknameEditText.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                    currentTextLengthTextView.text =
                        getString(R.string.record_voice_nickname_number, s?.length)
                    if (nickNameText.isEmpty()) emptyNickName()
                    else if (nickNameText.isNotEmpty() && (currentState == 2 || currentState == 4)) onClickCompleteButton()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun onClickRecord() {
        binding.view.setOnClickListener {
            it.hideKeyboard()
        }

        binding.recordDefaultImageView.setOnClickListener {
            it.hideKeyboard()
            changeRecordState()
        }
    }

    private fun changeRecordState() {
        when (currentState) {
            STATE_NORMAL -> {
                currentState = STATE_RECORD
            }
            STATE_RECORD -> {
                currentState = STATE_RECORD_STOP
            }
            STATE_RECORD_STOP -> {
                currentState = STATE_PLAYING
            }
            STATE_PLAYING -> {
                currentState = STATE_PAUSE
            }
            STATE_PAUSE -> {
                currentState = STATE_PLAYING
            }
        }
        changeResourceByState(currentState)
    }

    private fun changeResourceByState(recordState: Int) {
        when (recordState) {
            STATE_NORMAL -> {
                isRecord = false
                isPlaying = false
                currentState = STATE_NORMAL
                binding.recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_default)
            }
            STATE_RECORD -> {
                isRecord = true
                isPlaying = false
                currentState = STATE_RECORD
                MediaRecorderHelper.startRecording()
                showRecord()
            }
            STATE_RECORD_STOP -> {
                isRecord = false
                isPlaying = false
                currentState = STATE_RECORD_STOP
                MediaRecorderHelper.stopAndRelease()
                showRecordStop()
                showReRecordPlay()
            }
            STATE_PLAYING -> {
                isPlaying = true
                isRecord = false
                currentState = STATE_PLAYING
                showRecordPlaying()
                showReRecordStop()
                MediaPlayerHelper.startPlaying(MediaRecorderHelper.fileName) {
                    isPlaying = false
                    isRecord = false
                    currentState = STATE_PAUSE
                    showRecordStop()
                }
            }
            STATE_PAUSE -> {
                MediaPlayerHelper.pausePlaying()
                isRecord = false
                isPlaying = false
                currentState = STATE_PAUSE
                showRecordPause()
            }
        }
    }

    private fun showRecord() {
        emptyNickName()

        binding.apply {
            maximumTextView.visibility = View.INVISIBLE
            recordStatusTextView.setText(R.string.record_voice_record_to_press_button)
            recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_active)
            chronometer.isVisible = true
            chronometer.typeface = ResourcesCompat.getFont(requireContext(), R.font.font)
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
            chronometer.setOnChronometerTickListener {
                if (it.text == getString(R.string.record_voice_maximum_minute) && nickNameText.isNotEmpty()) {
                    it.stop()
                    recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_play)
                    recordStatusTextView.setText(R.string.record_voice_listen)
                    isRecord = false
                    isPlaying = false
                    currentState = STATE_RECORD_STOP
                    MediaRecorderHelper.stopAndRelease()
                    showReRecordPlay()
                    onClickCompleteButton()
                }
            }
        }
    }

    private fun showRecordStop() {
        if (nickNameText.isNotEmpty() && currentState == 2) onClickCompleteButton()

        binding.apply {
            recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_play)
            recordStatusTextView.setText(R.string.record_voice_listen)
            chronometer.stop()
        }
    }

    private fun showRecordPlaying() {
        if (nickNameText.isNotEmpty() && currentState == 3) onClickCompleteButton()

        binding.apply {
            recordStatusTextView.setText(R.string.record_voice_playing)
            recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_stop)
        }
    }

    private fun showRecordPause() {
        if (nickNameText.isNotEmpty() && currentState == 4) onClickCompleteButton()

        binding.apply {
            recordStatusTextView.setText(R.string.record_voice_listen)
            recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_play)
        }
    }

    private fun showReRecordStop() {
        if (binding.recordDefaultImageView.drawable.constantState == ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_icon_record_stop,
                null
            )?.constantState
        ) {
            binding.reRecordImageView.setOnClickListener {
                emptyNickName()
                binding.reRecordImageView.isVisible = false
                binding.reRecordTextView.isVisible = false
                binding.maximumTextView.isVisible = true
                binding.chronometer.isVisible = false
                binding.recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_default)
                isPlaying = false
                isRecord = false
                currentState = STATE_NORMAL
                MediaPlayerHelper.stopAndRelease()
            }
        }
    }

    private fun showReRecordPlay() {
        binding.apply {
            reRecordImageView.isVisible = true
            reRecordTextView.isVisible = true

            if (recordDefaultImageView.drawable.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_icon_record_play,
                    null
                )?.constantState
            ) {
                reRecordImageView.setOnClickListener {
                    emptyNickName()
                    reRecordImageView.isVisible = false
                    reRecordTextView.isVisible = false
                    maximumTextView.isVisible = true
                    chronometer.isVisible = false
                    binding.recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_default)
                    currentState = STATE_NORMAL
                    MediaPlayerHelper.stopAndRelease()
                }
            }
        }
    }

    private fun onClickCancelButton() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun emptyNickName() {
        binding.apply {
            completeButton.isEnabled = false
            completeButton.setBackgroundResource(R.drawable.background_grayscale1_radius12)
        }
    }

    private fun onClickCompleteButton() {
        val audioPath: Uri = file.toUri()
        val voiceFile = File(audioPath.path!!)

        val part: ArrayList<MultipartBody.Part> = ArrayList()
        part.add(getBody("nickname", nickNameText))
        part.add(getBody("message_type", "VOICE"))
        part.add(getAudioBody("voice_file", voiceFile))
        part.add(getBody("sticker_color", StickerColorType.values().random().toString()))
        part.add(getBody("sticker_angle", randomAngle()))

        binding.apply {
            completeButton.isEnabled = true
            completeButton.setBackgroundResource(R.drawable.backgroud_grayscale1_radius12_click)

            completeButton.setOnClickListener {
                showAds(requireActivity())

                detailViewModel.postReplyUserComment(
                    safeArgs.roomCode,
                    part
                ).also {
                    if (safeArgs.host) createCommentByAdmin()
                    else createComment()
                    dismiss()
                }
            }
        }
    }

    private fun createCommentByAdmin() {
        val action =
            RecordVoiceDialogFragmentDirections.actionRecordVoiceDialogFragmentToDetailFragment(
                safeArgs.roomCode,
                "",
                true,
                safeArgs.bookmark
            )
        findNavController().navigate(action)
        findNavController().popBackStack()
    }

    private fun createComment() {
        val action =
            RecordVoiceDialogFragmentDirections.actionRecordVoiceDialogFragmentToDetailFragment(
                safeArgs.roomCode, "", false, safeArgs.bookmark
            )
        findNavController().navigate(action)
        findNavController().popBackStack()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else false
        if (!permissionToRecordAccepted) dismiss()
    }

    override fun onDestroy() {
        if (isRecord) {
            binding.chronometer.stop()
            MediaRecorderHelper.stopAndRelease()
        }
        if (isPlaying) MediaPlayerHelper.stopAndRelease()
        super.onDestroy()
    }
}