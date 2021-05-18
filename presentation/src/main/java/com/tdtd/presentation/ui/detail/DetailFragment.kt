package com.tdtd.presentation.ui.detail

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tdtd.domain.entity.MakeRoomType
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentDetailBinding
import com.tdtd.presentation.ui.main.MainViewModel
import com.tdtd.presentation.util.Constants.STATE_PAUSE
import com.tdtd.presentation.util.Constants.STATE_PLAYING
import com.tdtd.presentation.util.MediaPlayerHelper
import com.tdtd.presentation.util.getNavigationResult
import com.tdtd.presentation.util.playerFormat
import com.tdtd.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_text_comment.*
import kotlinx.android.synthetic.main.fragment_voice_comment.*
import kotlinx.coroutines.*


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail),
    CoroutineScope by MainScope() {

    private val detailViewModel: DetailViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val safeArgs: DetailFragmentArgs by navArgs()
    private lateinit var detailAdapter: DetailAdapter
    private var type: String = ""
    private var isFavorite: Boolean = false
    private var endTime = 0
    private var currentState = STATE_PLAYING
    private var isPlaying = false
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private var job = SupervisorJob()
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.Default + job)
    private var handler = Handler(Looper.getMainLooper())
    private var currentProgress = 0

    override fun initViews() {
        super.initViews()

        initBindings()
        setAdapter()
        onClickFavoritesButton()
        onClickBackButton()
        onClickSharedButton()
        onClickMoreButton()
        onClickLeaveRoomButton()
    }

    override fun initObserves() {
        super.initObserves()

        if (safeArgs.host)
            hostDetailFragment()
        else
            userDetailFragment()

        getNavigationResult<String>(R.id.detailFragment, "detail") {
            findNavController().navigateUp()
            detailViewModel.deleteRoom(it)
        }

        detailViewModel.deleteRoomValue.observe(viewLifecycleOwner) { text ->
            if (text.isNotEmpty()) {
                requireActivity().showToast(
                    getString(R.string.toast_leave_room_success),
                    requireView()
                )
            }
        }

        detailViewModel.alreadyReportEvent.observe(viewLifecycleOwner, Observer {
            requireActivity().showToast(
                getString(R.string.dialog_report_reduplicate),
                requireView()
            )
        })

        detailViewModel.notMineEvent.observe(viewLifecycleOwner, Observer {
            requireActivity().showToast(getString(R.string.dialog_delete_not_mine), requireView())
        })

        detailViewModel.detailRoom.observe(viewLifecycleOwner, Observer { detailRoom ->
            binding.titleTextView.text = detailRoom.result.title

            if (detailRoom.result.comments.isEmpty() && safeArgs.host) {
                hideDetailRecyclerView()
            } else if (detailRoom.result.comments.isEmpty() && !safeArgs.host) {
                hideEmptyDetailRoom()
            } else {
                hideNoReplyTextView()
                hideEmptyDetailRoom()
                showDetailRecyclerView()
            }

            type = when (detailRoom.result.type) {
                MakeRoomType.TEXT -> {
                    startWriteTextDetailFragment()
                    "text"
                }
                MakeRoomType.VOICE -> {
                    startRecordVoiceDialogFragment()
                    "voice"
                }
            }
        })

        detailViewModel.getRoomDetailByRoomCode(safeArgs.roomCode)
    }

    private fun initBindings() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = detailViewModel
        isFavorite = safeArgs.bookmark
    }

    private fun setAdapter() {
        detailAdapter = DetailAdapter { comments ->
            if (comments.text != null) {
                showTextCommentBottomSheet(
                    comments.nickname,
                    comments.text,
                    comments.id,
                    comments.is_mine
                )
            } else if (comments.text == null) {
                showVoiceCommentBottomSheet(
                    comments.nickname,
                    comments.id,
                    comments.is_mine,
                    comments.voice_file_url
                )
            }
        }
        binding.detailRecyclerView.adapter = detailAdapter
    }

    private fun showTextCommentBottomSheet(
        name: String,
        content: String?,
        id: Long?,
        mine: Boolean
    ) {
        bottomSheetBehavior = BottomSheetBehavior.from(textCommentBottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_EXPANDED

            val nickName = requireActivity().findViewById<TextView>(R.id.nicknameTextView)
            val contents = requireActivity().findViewById<TextView>(R.id.contentsTextView)
            val closeImageView = requireActivity().findViewById<ImageView>(R.id.closeImageView)
            val report = requireActivity().findViewById<ImageView>(R.id.reportImageView)
            val remove = requireActivity().findViewById<ImageView>(R.id.removeImageView)

            nickName.text = name
            contents.text = content

            closeImageView.setOnClickListener {
                bottomSheetBehavior.state =
                    BottomSheetBehavior.STATE_HIDDEN
            }

            report.setOnClickListener {
                if (mine) requireActivity().showToast(
                    getString(R.string.dialog_report_mine),
                    requireView()
                )
                else showReportCommentDialog(id!!)
            }
            remove.setOnClickListener {
                if (safeArgs.host) showDeleteByHostCommentDialog(id!!)
                else showDeleteCommentDialog(id!!)
            }
        }
    }

    private fun showVoiceCommentBottomSheet(
        name: String,
        id: Long?,
        mine: Boolean,
        voiceFileUrl: String?
    ) {
        bottomSheetBehavior = BottomSheetBehavior.from(voiceCommentBottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_EXPANDED

            val nickName = requireActivity().findViewById<TextView>(R.id.nicknameVoiceTextView)
            val closeImageView = requireActivity().findViewById<ImageView>(R.id.closeVoiceImageView)
            val report = requireActivity().findViewById<ImageView>(R.id.reportVoiceImageView)
            val remove = requireActivity().findViewById<ImageView>(R.id.removeVoiceImageView)
            val recordImageView =
                requireActivity().findViewById<ImageView>(R.id.recordDefaultImageView)
            val endTimeTextView = requireActivity().findViewById<TextView>(R.id.endTimeTextView)
            val progressBar = requireActivity().findViewById<ProgressBar>(R.id.progressBar)

            nickName.text = name
            recordImageView.setImageResource(R.drawable.ic_speak_play_32)

            closeImageView.setOnClickListener {
                bottomSheetBehavior.state =
                    BottomSheetBehavior.STATE_HIDDEN
            }

            report.setOnClickListener {
                if (mine) requireActivity().showToast(
                    getString(R.string.dialog_report_mine),
                    requireView()
                )
                else showReportCommentDialog(id!!)
            }
            remove.setOnClickListener {
                if (safeArgs.host) showDeleteByHostCommentDialog(id!!)
                else showDeleteCommentDialog(id!!)
            }

            initPlaying()

            mediaPlayer.let { mediaPlayer ->
                mediaPlayer?.setDataSource(voiceFileUrl)
                mediaPlayer?.prepare()
                currentProgress = mediaPlayer!!.currentPosition
            }

            if (mediaPlayer!!.duration.toLong() > 60000) endTimeTextView.text =
                getString(R.string.record_voice_maximum_minute)
            else playerFormat(mediaPlayer?.duration!!.toLong(), endTimeTextView)

            endTime = mediaPlayer!!.duration / 100
            progressBar.max = endTime

            recordImageView.setOnClickListener {
                changeByState(voiceFileUrl, recordImageView, progressBar)
            }
        }
    }

    private fun initPlaying() {
        mediaPlayer = MediaPlayer()
        MediaPlayerHelper.stopAndRelease()
        job.cancelChildren()
        progressBar.progress = 0
        currentState = STATE_PLAYING
    }

    private fun changeByState(
        url: String?,
        imageView: ImageView,
        progressBar: ProgressBar
    ) {
        when (currentState) {
            STATE_PLAYING -> {
                isPlaying = true
                currentState = STATE_PAUSE
                imageView.setImageResource(R.drawable.ic_speak_stop_32)

                scope.launch {
                    if (isActive) {
                        while (currentProgress < endTime) {
                            currentProgress++
                            try {
                                delay(100)
                            } finally {
                                handler.post { progressBar.progress = currentProgress }
                            }
                        }
                    }
                }

                MediaPlayerHelper.startPlaying(url!!) {
                    isPlaying = false
                    currentState = STATE_PLAYING
                    imageView.setImageResource(R.drawable.ic_speak_play_32)
                    job.cancelChildren()
                    currentProgress = mediaPlayer!!.currentPosition
                }
            }

            STATE_PAUSE -> {
                MediaPlayerHelper.pausePlaying()
                isPlaying = false
                currentState = STATE_PLAYING
                imageView.setImageResource(R.drawable.ic_speak_play_32)
                job.cancelChildren()
            }
        }
    }

    private fun showDeleteCommentDialog(id: Long) {
        val action =
            DetailFragmentDirections.actionDetailFragmentToCustomDialogFragment(
                "",
                id,
                R.layout.dialog_delete_reply,
                isFavorite,
                false
            )
        findNavController().navigate(action)
    }

    private fun showDeleteByHostCommentDialog(id: Long) {
        val action =
            DetailFragmentDirections.actionDetailFragmentToCustomDialogFragment(
                "",
                id,
                R.layout.dialog_delete_reply_admin,
                isFavorite,
                true
            )
        findNavController().navigate(action)
    }

    private fun showReportCommentDialog(id: Long) {
        val action =
            DetailFragmentDirections.actionDetailFragmentToCustomDialogFragment(
                "",
                id,
                R.layout.dialog_report_reply,
                isFavorite,
                safeArgs.host
            )
        findNavController().navigate(action)
    }

    private fun startRecordVoiceDialogFragment() {
        binding.writeButton.setOnClickListener {
            val action =
                DetailFragmentDirections.actionDetailFragmentToRecordVoiceDialogFragment(
                    safeArgs.roomCode, safeArgs.host, isFavorite
                )
            findNavController().navigate(action)
        }
    }

    private fun startWriteTextDetailFragment() {
        binding.writeButton.setOnClickListener {
            val action =
                DetailFragmentDirections.actionDetailFragmentToWriteTextDialogFragment(
                    safeArgs.roomCode, safeArgs.host, isFavorite
                )
            findNavController().navigate(action)
        }
    }

    private fun onClickMoreButton() {
        binding.moreButton.setOnClickListener {
            val action =
                DetailFragmentDirections.actionDetailFragmentToDetailSharedBottomSheetFragment(
                    safeArgs.roomCode,
                    safeArgs.createdAt,
                    isFavorite
                )
            findNavController().navigate(action)
        }
    }

    private fun onClickSharedButton() {
        binding.sharedLinkButton.setOnClickListener {
            val action =
                DetailFragmentDirections.actionDetailFragmentToDetailSharedBottomSheetFragment(
                    safeArgs.roomCode,
                    safeArgs.createdAt,
                    isFavorite
                )
            findNavController().navigate(action)
            showNoReplyTextView()
        }
    }

    private fun onClickFavoritesButton() {
        binding.favoritesButton.isSelected = isFavorite

        binding.favoritesButton.setOnClickListener {
            when (isFavorite) {
                true -> {
                    binding.favoritesButton.isSelected = false
                    isFavorite = false
                    mainViewModel.deleteBookmarkByRoomCode(safeArgs.roomCode)
                }
                false -> {
                    binding.favoritesButton.isSelected = true
                    isFavorite = true
                    mainViewModel.postBookmarkByRoomCode(safeArgs.roomCode)
                }
            }
        }
    }

    private fun onClickBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
            job.cancel()
            MediaPlayerHelper.stopAndRelease()
        }
    }

    private fun onClickLeaveRoomButton() {
        binding.leaveRoomButton.setOnClickListener {
            val action =
                DetailFragmentDirections.actionDetailFragmentToCustomDialogFragment(
                    safeArgs.roomCode,
                    0,
                    R.layout.dialog_leave_room,
                    isFavorite,
                    safeArgs.host
                )
            findNavController().navigate(action)
        }
    }

    private fun hostDetailFragment() {
        binding.moreButton.isVisible = true
        binding.leaveRoomButton.isVisible = false
        binding.sharedLinkButton.isVisible = true
        binding.sharedLinkTextView.isVisible = true
        binding.sharedFriendTextView.isVisible = false
    }

    private fun userDetailFragment() {
        binding.moreButton.isVisible = false
        binding.leaveRoomButton.isVisible = true
        binding.sharedLinkButton.isVisible = false
        binding.sharedLinkTextView.isVisible = false
        binding.sharedFriendTextView.isVisible = true
    }

    private fun hideEmptyDetailRoom() {
        binding.sharedLinkTextView.isVisible = false
    }

    private fun showNoReplyTextView() {
        binding.sharedLinkButton.isVisible = false
        binding.sharedLinkTextView.isVisible = false
        binding.sharedNoReplyTextView.isVisible = true
    }

    private fun hideNoReplyTextView() {
        binding.sharedLinkButton.isVisible = false
        binding.sharedLinkTextView.isVisible = false
        binding.sharedNoReplyTextView.isVisible = false
        binding.sharedFriendTextView.isVisible = false
    }

    private fun showDetailRecyclerView() {
        binding.detailRecyclerView.isVisible = true
    }

    private fun hideDetailRecyclerView() {
        binding.detailRecyclerView.isVisible = false
    }

    override fun onDestroy() {
        if (isPlaying) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
        job.cancel()
        cancel()
        super.onDestroy()
    }
}
