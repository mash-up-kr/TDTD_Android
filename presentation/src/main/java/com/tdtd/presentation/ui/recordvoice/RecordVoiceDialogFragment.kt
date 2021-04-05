package com.tdtd.presentation.ui.recordvoice

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
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
import com.tdtd.presentation.databinding.DetailVoiceBottomSheetBinding
import com.tdtd.presentation.util.toPx
import java.io.IOException
import java.util.*
import kotlin.concurrent.timer

class RecordVoiceDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DetailVoiceBottomSheetBinding
    private var mStartPlaying = true
    private var mStartRecording = true
    private var mediaPlayer: MediaPlayer? = null
    private var mediaRecorder: MediaRecorder? = null
    private var fileName: String = ""
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.detail_voice_bottom_sheet, container, false)
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

        onClickCancelButton()
        initRecorder()
        initParentHeight()
        setRoomEditFocus()
        setRoomEditView()
        setRecording()
        setCompleteButton()
    }

    private fun initRecorder() {
        fileName = "${requireActivity().externalCacheDir?.absolutePath}/recording.3gp"
        ActivityCompat.requestPermissions(
            requireActivity(),
            permissions,
            REQUEST_RECORD_AUDIO_PERMISSION
        )
    }

    private fun setRoomEditFocus() {
        binding.completeButton.isEnabled = false

        binding.nicknameEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) view.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
            else view.setBackgroundResource(R.drawable.background_beige2_stroke1_beige3_radius16)
        }
    }

    private fun setRoomEditView() {
        binding.currentTextLengthTextView.text = getString(R.string.recode_voice_nickname_number, 0)

        binding.nicknameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.nicknameEditText.setBackgroundResource(R.drawable.background_beige2_stroke1_gray2_radius16)
                binding.currentTextLengthTextView.text =
                    getString(R.string.recode_voice_nickname_number, s?.length)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun onClickCancelButton() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setRecording() {
        binding.recordDefaultImageView.setOnClickListener {
            when (binding.recordDefaultImageView.background.constantState) {
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_icon_record_default,
                    null
                )?.constantState -> {
                    it.setBackgroundResource(R.drawable.ic_icon_record_active)
                    startRecording()
                }

                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_icon_record_active,
                    null
                )?.constantState -> {
                    it.setBackgroundResource(R.drawable.ic_icon_record_play)
                    stopRecording()
                }

                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_icon_record_play,
                    null
                )?.constantState -> {
                    it.setBackgroundResource(R.drawable.ic_icon_record_stop)
                    startPlaying()
                }

                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_icon_record_stop,
                    null
                )?.constantState -> {
                    it.setBackgroundResource(R.drawable.ic_icon_record_play)
                    stopPlaying()
                }
            }
        }
    }

    private fun startRecording() {
        binding.recordStatusTextView.setText(R.string.recode_voice_record_to_press_button)
        mediaRecorder = MediaRecorder().apply {
            mStartRecording = !mStartRecording
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            start()

            isRunning = !isRunning
            if (isRunning) {
                timerTask = timer(period = 10) {
                    time++
                    val sec = time / 100

                    requireActivity().runOnUiThread {
                        binding.apply {
                            maximumTextView.isVisible = false
                            timerTextView.isVisible = true
                            "00:$sec".also { timerTextView.text = it }
                        }
                    }
                }
            }
        }
    }

    private fun stopRecording() {
        binding.recordStatusTextView.setText(R.string.recode_voice_listen)
        mStartRecording = !mStartRecording
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        isRunning = !isRunning
        timerTask?.cancel()
    }

    private fun startPlaying() {
        binding.recordStatusTextView.setText(R.string.recode_voice_playing)
        mStartPlaying = !mStartPlaying
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        isRunning = !isRunning
        if (isRunning) {
            timerTask = timer(period = 10) {
                time++
                val sec = time / 100

                requireActivity().runOnUiThread {
                    binding.apply {
                        maximumTextView.isVisible = false
                        timerTextView.isVisible = true
                        "00:$sec".also { timerTextView.text = it }
                    }
                }
            }
        }
    }

    private fun stopPlaying() {
        binding.recordStatusTextView.setText(R.string.recode_voice_listen)
        mStartPlaying = !mStartPlaying
        mediaPlayer?.release()
        mediaPlayer = null
        isRunning = !isRunning
        timerTask?.cancel()
    }

    private fun reset() {
        binding.apply {
            recordDefaultImageView.setBackgroundResource(R.drawable.ic_icon_record_default)
            maximumTextView.isVisible = true
            timerTextView.isVisible = false
        }

        timerTask?.cancel()
        time = 0
        isRunning = false
    }

    private fun setCompleteButton() {
        if (binding.nicknameEditText.text?.isNotEmpty() == true && binding.timerTextView.isVisible) {
            binding.completeButton.apply {
                isEnabled = true
                setBackgroundResource(R.drawable.backgroud_grayscale1_radius12_click)
            }
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

    override fun onPause() {
        super.onPause()
        mediaRecorder?.release()
        mediaRecorder = null
    }

    companion object {
        const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}
