package com.tdtd.presentation.ui.detail

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.tdtd.domain.entity.RoomTypeEntity
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentDetailBinding
import com.tdtd.presentation.ui.main.MainViewModel
import com.tdtd.presentation.utils.*
import com.tdtd.presentation.utils.Constants.STATE_PAUSE
import com.tdtd.presentation.utils.Constants.STATE_PLAYING
import dagger.hilt.android.AndroidEntryPoint
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
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private var currentProgress = 0
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun initViews() {
        super.initViews()

        initBindings()
        initAnalytics()
        roomDetailAnalytics()
        setAdapter()
        onClickFavoritesButton()
        onClickBackButton()
        onClickSharedButton()
        onClickMoreButton()
        onClickLeaveRoomButton()
    }

    override fun initObserves() {
        super.initObserves()

        getNavigationResult<String>(R.id.detailFragment, "detail_leave_room") { text ->
            requireActivity().showToast(text, requireView())
            requireView().postDelayed(
                { findNavController().navigateSafeUp(findNavController().currentDestination!!.id) },
                300
            )
        }

        getNavigationResult<String>(R.id.detailFragment, "modify_room_name") { newTitle ->
            requireView().postDelayed({
                requireActivity().showToast(
                    getString(R.string.toast_modify_room_name),
                    requireView()
                )
                binding.titleTextView.text = newTitle
            }, 300)
        }

        detailViewModel.apiFailEvent.observe(viewLifecycleOwner) {
            requireActivity().showToast(getString(R.string.toast_error_occurred), requireView())
        }

        detailViewModel.detailRoom.observe(viewLifecycleOwner) { detailRoom ->
            binding.titleTextView.text = detailRoom.result.title

            if (detailRoom.result.comments.isEmpty() && safeArgs.host) {
                hideDetailRecyclerView()
                hostDetailFragment()
            } else if (detailRoom.result.comments.isEmpty() && safeArgs.host.not()) {
                hideDetailRecyclerView()
                userDetailFragment()
            } else if (detailRoom.result.comments.isNotEmpty()) {
                hideNoReplyTextView()
                showDetailRecyclerView()
            }

            type = when (detailRoom.result.typeEntity) {
                RoomTypeEntity.TEXT -> {
                    showWriteButton()
                    startWriteTextDetailFragment()
                    "text"
                }
                RoomTypeEntity.VOICE -> {
                    showRecordButton()
                    startRecordVoiceDialogFragment()
                    "voice"
                }
            }
        }
        detailViewModel.getRoomDetailByRoomCode(safeArgs.roomCode)
    }

    private fun initBindings() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = detailViewModel
        isFavorite = safeArgs.bookmark
        when (safeArgs.host) {
            true -> hostDetailFragment()
            false -> userDetailFragment()
        }
    }

    private fun initAnalytics() {
        firebaseAnalytics = Firebase.analytics
    }

    private fun roomDetailAnalytics() {
        firebaseAnalytics.logEvent("RoomDetailView", null)
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

        binding.detailRecyclerView.run {
            adapter = detailAdapter
            setHasFixedSize(true)
        }

        hideNoReplyTextView()
        showDetailRecyclerView()
    }

    private fun showTextCommentBottomSheet(
        name: String,
        content: String?,
        id: Long?,
        mine: Boolean
    ) {
        val textCommentBottomSheet =
            requireView().findViewById<ConstraintLayout>(R.id.textCommentBottomSheet)

        bottomSheetBehavior = BottomSheetBehavior.from(textCommentBottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_EXPANDED

            with(binding.textCommentBottomSheet) {
                nicknameTextView.text = name

                contentsTextView.apply {
                    text = content
                    scrollTo(0, 0)
                    movementMethod = ScrollingMovementMethod.getInstance()
                }

                closeImageView.setOnClickListener {
                    bottomSheetBehavior.state =
                        BottomSheetBehavior.STATE_HIDDEN
                }

                reportImageView.setOnClickListener {
                    if (mine) requireActivity().showToast(
                        getString(R.string.dialog_report_mine),
                        requireView()
                    )
                    else showReportCommentDialog(id!!)
                }

                removeImageView.setOnClickListener {
                    if (safeArgs.host) showDeleteByHostCommentDialog(id!!)
                    else if (!mine) requireActivity().showToast(
                        getString(R.string.dialog_delete_not_mine),
                        requireView()
                    )
                    else showDeleteCommentDialog(id!!)
                }
            }
        }
    }

    private fun showVoiceCommentBottomSheet(
        name: String,
        id: Long?,
        mine: Boolean,
        voiceFileUrl: String?
    ) {
        val voiceCommentBottomSheet =
            requireView().findViewById<ConstraintLayout>(R.id.voiceCommentBottomSheet)

        bottomSheetBehavior = BottomSheetBehavior.from(voiceCommentBottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_EXPANDED

            with(binding.voiceCommentBottomSheet) {
                nicknameVoiceTextView.text = name
                recordDefaultImageView.setImageResource(R.drawable.ic_speak_play_32)

                closeVoiceImageView.setOnClickListener {
                    bottomSheetBehavior.state =
                        BottomSheetBehavior.STATE_HIDDEN
                }

                reportVoiceImageView.setOnClickListener {
                    if (mine) requireActivity().showToast(
                        getString(R.string.dialog_report_mine),
                        requireView()
                    )
                    else showReportCommentDialog(id!!)
                }

                removeVoiceImageView.setOnClickListener {
                    if (safeArgs.host) showDeleteByHostCommentDialog(id!!)
                    else if (!mine) requireActivity().showToast(
                        getString(R.string.dialog_delete_not_mine),
                        requireView()
                    )
                    else showDeleteCommentDialog(id!!)
                }
            }

            mediaPlayer = MediaPlayer()
            MediaPlayerHelper.stopAndRelease()
            job.cancelChildren()
            currentState = STATE_PLAYING

            val progressBar = voiceCommentBottomSheet.findViewById<ProgressBar>(R.id.progressBar)
            progressBar.progress = 0

            mediaPlayer.let { mediaPlayer ->
                mediaPlayer?.setDataSource(voiceFileUrl)
                mediaPlayer?.prepare()
                currentProgress = mediaPlayer!!.currentPosition
            }

            if (mediaPlayer!!.duration.toLong() > 60000) binding.voiceCommentBottomSheet.endTimeTextView.text =
                getString(R.string.record_voice_maximum_minute)
            else playerFormat(
                mediaPlayer?.duration!!.toLong(),
                binding.voiceCommentBottomSheet.endTimeTextView
            )

            endTime = mediaPlayer!!.duration / 100
            progressBar.max = endTime

            binding.voiceCommentBottomSheet.recordDefaultImageView.run {
                setOnClickListener { changeByState(voiceFileUrl, this, progressBar) }
            }
        }
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
                safeArgs.roomCode,
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
                safeArgs.roomCode,
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
        binding.recordButton.onThrottleClick {
            val action =
                DetailFragmentDirections.actionDetailFragmentToRecordVoiceDialogFragment(
                    safeArgs.roomCode, safeArgs.host, isFavorite
                )
            findNavController().navigate(action)
        }
    }

    private fun startWriteTextDetailFragment() {
        binding.writeButton.onThrottleClick {
            val action =
                DetailFragmentDirections.actionDetailFragmentToWriteTextDialogFragment(
                    safeArgs.roomCode, safeArgs.host, isFavorite
                )
            findNavController().navigate(action)
        }
    }

    private fun onClickMoreButton() {
        binding.moreButton.onThrottleClick {
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
        binding.sharedLinkButton.onThrottleClick {
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
                    val bundle = Bundle()
                    bundle.putString("value", "off")
                    firebaseAnalytics.logEvent("Favorite", bundle)
                }
                false -> {
                    binding.favoritesButton.isSelected = true
                    isFavorite = true
                    mainViewModel.postBookmarkByRoomCode(safeArgs.roomCode)
                    val bundle = Bundle()
                    bundle.putString("value", "on")
                    firebaseAnalytics.logEvent("Favorite", bundle)
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
        binding.leaveRoomButton.onThrottleClick {
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

    private fun showWriteButton() {
        binding.apply {
            titleTextView.leftDrawable(R.drawable.badge)
            writeButton.isVisible = true
            recordButton.isVisible = false
        }
    }

    private fun showRecordButton() {
        binding.apply {
            titleTextView.leftDrawable(R.drawable.property_1record)
            writeButton.isVisible = false
            recordButton.isVisible = true
        }
    }

    override fun onDestroy() {
        if (mediaPlayer != null) {
            if (isPlaying) mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
        job.cancel()
        cancel()
        super.onDestroy()
    }
}
