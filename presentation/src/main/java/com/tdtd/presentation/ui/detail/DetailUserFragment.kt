package com.tdtd.presentation.ui.detail

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tdtd.domain.entity.MakeRoomType
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentDetailUserBinding
import com.tdtd.presentation.ui.dialog.CustomDialogFragment
import com.tdtd.presentation.ui.main.MainViewModel
import com.tdtd.presentation.ui.reply.RecordVoiceDialogFragment
import com.tdtd.presentation.ui.reply.WriteTextDialogFragment
import com.tdtd.presentation.util.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_text_comment.*

@AndroidEntryPoint
class DetailUserFragment : BaseFragment<FragmentDetailUserBinding>(R.layout.fragment_detail_user) {

    private val detailViewModel: DetailViewModel by viewModels()
    private val safeArgs: DetailUserFragmentArgs by navArgs()
    private lateinit var detailAdapter: DetailAdapter
    private var type: String = ""
    private val mainViewModel: MainViewModel by viewModels()
    private val preferenceManager = PreferenceManager(requireActivity().applicationContext)
    private var isFavorite: Boolean = false
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun initViews() {
        super.initViews()

        initBindings()
        setAdapter()
        onClickFavoritesButton()
        onClickBackButton()
        onClickLeaveRoomButton()
    }

    override fun initObserves() {
        super.initObserves()

        detailViewModel.detailRoom.observe(viewLifecycleOwner, Observer { detailRoom ->
            binding.titleTextView.text = detailRoom.result.title

            if (detailRoom.result.comments.isEmpty()) {
                showEmptyDetailRoom()
                hideDetailRecyclerView()
            } else {
                showDetailRecyclerView()
                hideEmptyDetailRoom()
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
    }

    private fun setAdapter() {
        detailAdapter = DetailAdapter { comments ->
            if (type == "text")
                showComments(
                    comments.nickname,
                    comments.text,
                    comments.id,
                    R.layout.fragment_text_comment
                )
            if (type == "voice")
                showComments(
                    comments.nickname,
                    comments.text,
                    comments.id,
                    R.layout.fragment_record_voice
                )
        }
        binding.detailRecyclerView.adapter = detailAdapter
    }

    private fun showComments(name: String, contents: String?, commentId: Long?, layoutId: Int) {
        val dialog = DetailCommentBottomSheetFragment(layoutId)
        dialog.arguments = bundleOf(
            "nickName" to name,
            "contents" to contents,
            "id" to commentId
        )
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun showTextCommentBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(textCommentBottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun showEmptyDetailRoom() {
        binding.sharedLinkTextView.isVisible = true
    }

    private fun hideEmptyDetailRoom() {
        binding.sharedLinkTextView.isVisible = false
    }

    private fun showDetailRecyclerView() {
        binding.detailRecyclerView.isVisible = true
    }

    private fun hideDetailRecyclerView() {
        binding.detailRecyclerView.isVisible = false
    }

    private fun startRecordVoiceDialogFragment() {
        binding.writeButton.setOnClickListener {
            val bottomSheet = RecordVoiceDialogFragment()
            bottomSheet.arguments = bundleOf("roomCode" to safeArgs.roomCode)
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun startWriteTextDetailFragment() {
        binding.writeButton.setOnClickListener {
            val bottomSheet = WriteTextDialogFragment()
            bottomSheet.arguments = bundleOf("roomCode" to safeArgs.roomCode)
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun onClickFavoritesButton() {
        binding.favoritesButton.isSelected = preferenceManager.getFavorite()

        binding.favoritesButton.setOnClickListener {
            when (isFavorite) {
                true -> {
                    binding.favoritesButton.isSelected = false
                    isFavorite = false
                    preferenceManager.setFavorite(false)
                    mainViewModel.deleteBookmarkByRoomCode(safeArgs.roomCode)
                }
                false -> {
                    isFavorite = true
                    preferenceManager.setFavorite(true)
                    mainViewModel.postBookmarkByRoomCode(safeArgs.roomCode)
                }
            }
        }
    }

    private fun onClickBackButton() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun onClickLeaveRoomButton() {
        binding.leaveRoomButton.setOnClickListener {
            val dialog = CustomDialogFragment(R.layout.dialog_leave_room)
            dialog.arguments = bundleOf("roomCode" to safeArgs.roomCode)
            dialog.show(childFragmentManager, dialog.tag)
        }
    }
}