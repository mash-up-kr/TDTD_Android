package com.tdtd.presentation.ui.recordvoice

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.FragmentRecordVoiceBinding
import com.tdtd.presentation.util.Constants
import com.tdtd.presentation.util.Constants.STATE_NORMAL
import com.tdtd.presentation.util.Constants.STATE_PAUSE
import com.tdtd.presentation.util.Constants.STATE_PLAYING
import com.tdtd.presentation.util.Constants.STATE_RECORD
import com.tdtd.presentation.util.Constants.STATE_RECORD_STOP
import com.tdtd.presentation.util.MediaPlayerHelper
import com.tdtd.presentation.util.MediaRecorderHelper
import com.tdtd.presentation.util.initParentHeight

class RecordVoiceDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRecordVoiceBinding
    private lateinit var file: String
    private var currentState = STATE_NORMAL
    private var isPlaying = false
    private var isRecord = false
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

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
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initParentHeight(requireActivity(), view)
        initRecord()
        onClickCancelButton()
        setNickNameEditFocus()
        observeNickNameEditChange()
    }

    // MediaPlayer sound delayed onCreate()
    override fun onResume() {
        super.onResume()
        onClickRecord()
    }

    private fun initRecord() {
        file = "${requireActivity().externalCacheDir?.absolutePath}/recording.3gp"
        MediaRecorderHelper.fileName = file

        ActivityCompat.requestPermissions(
            requireActivity(),
            permissions,
            Constants.REQUEST_RECORD_AUDIO_PERMISSION
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
            }
        }
    }

    private fun observeNickNameEditChange() {
        binding.nicknameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.apply {
                    nicknameEditText.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                    currentTextLengthTextView.text =
                        getString(R.string.recode_voice_nickname_number, s?.length)
                }

                if (s!!.isNotEmpty() && currentState == 2) onClickCompleteButton()
                else emptyNickName()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun onClickRecord() {
        binding.recordDefaultImageView.setOnClickListener {
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
                Log.v(TAG, "STATE_NORMAL isRecord:$isRecord isPlaying:$isPlaying")
            }
            STATE_RECORD -> {
                isRecord = true
                isPlaying = false
                currentState = STATE_RECORD
                MediaRecorderHelper.startRecording()
                showRecord()
                Log.v(TAG, "STATE_RECORD isRecord:$isRecord isPlaying:$isPlaying")
            }
            STATE_RECORD_STOP -> {
                isRecord = false
                isPlaying = false
                currentState = STATE_RECORD_STOP
                MediaRecorderHelper.stopAndRelease()
                showRecordStop()
                showReRecordPlay()
                Log.v(TAG, "STATE_RECORD_STOP isRecord:$isRecord isPlaying:$isPlaying")
            }
            STATE_PLAYING -> {
                isPlaying = true
                isRecord = false
                currentState = STATE_PLAYING
                showRecordPlaying()
                showReRecordStop()
                Log.v(TAG, "STATE_PLAYING isRecord:$isRecord isPlaying:$isPlaying")
                MediaPlayerHelper.startPlaying(MediaRecorderHelper.fileName) {
                    binding.recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_play)
                    isPlaying = false
                    isRecord = false
                    currentState = STATE_PAUSE
                    Log.v(TAG, "Playing Completed isRecord:$isRecord isPlaying:$isPlaying")
                }
            }
            STATE_PAUSE -> {
                MediaPlayerHelper.pausePlaying()
                isRecord = false
                isPlaying = false
                currentState = STATE_PAUSE
                showRecordPause()
                Log.v(TAG, "STATE_PAUSE isRecord:$isRecord isPlaying:$isPlaying")
            }
        }
    }

    private fun showRecord() {
        binding.apply {
            maximumTextView.visibility = View.INVISIBLE
            recordStatusTextView.setText(R.string.recode_voice_record_to_press_button)
            recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_active)
            chronometer.isVisible = true
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
            chronometer.setOnChronometerTickListener {
                if (it.text == getString(R.string.recode_voice_maximum_minute)) {
                    it.stop()
                    recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_play)
                    recordStatusTextView.setText(R.string.recode_voice_listen)
                    isRecord = false
                    isPlaying = false
                    currentState = STATE_RECORD_STOP
                    MediaRecorderHelper.stopAndRelease()
                    showReRecordPlay()
                }
            }
        }
    }

    private fun showRecordStop() {
        binding.apply {
            recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_play)
            recordStatusTextView.setText(R.string.recode_voice_listen)
            chronometer.stop()
        }
    }

    private fun showRecordPlaying() {
        binding.apply {
            recordStatusTextView.setText(R.string.recode_voice_playing)
            recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_stop)
        }
    }

    private fun showRecordPause() {
        binding.apply {
            recordStatusTextView.setText(R.string.recode_voice_listen)
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
                binding.reRecordImageView.isVisible = false
                binding.reRecordTextView.isVisible = false
                binding.maximumTextView.isVisible = true
                binding.chronometer.isVisible = false
                binding.recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_default)
                isPlaying = false
                isRecord = false
                currentState = STATE_NORMAL
                MediaPlayerHelper.stopAndRelease()
                Log.v(TAG, "RERECORD in Stop isRecord:$isRecord isPlaying:$isPlaying")
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
                    reRecordImageView.isVisible = false
                    reRecordTextView.isVisible = false
                    maximumTextView.isVisible = true
                    chronometer.isVisible = false
                    binding.recordDefaultImageView.setImageResource(R.drawable.ic_icon_record_default)
                    currentState = STATE_NORMAL
                    MediaPlayerHelper.stopAndRelease()
                    Log.v(TAG, "RERECORD in Play isRecord:$isRecord isPlaying:$isPlaying")
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
        binding.apply {
            completeButton.isEnabled = true
            completeButton.setBackgroundResource(R.drawable.backgroud_grayscale1_radius12_click)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == Constants.REQUEST_RECORD_AUDIO_PERMISSION) {
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

    companion object {
        const val TAG = "RecordVoiceFragment"
    }
}