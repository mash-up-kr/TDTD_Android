package com.tdtd.presentation.ui.detail

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.tdtd.domain.entity.MakeRoomType
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentDetailAdminBinding
import com.tdtd.presentation.entity.Comments
import com.tdtd.presentation.ui.main.MainViewModel
import com.tdtd.presentation.util.PreferenceManager
import com.tdtd.presentation.util.getNavigationResult
import com.tdtd.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_text_comment.*
import kotlinx.android.synthetic.main.fragment_voice_comment.*

@AndroidEntryPoint
class DetailAdminFragment :
    BaseFragment<FragmentDetailAdminBinding>(R.layout.fragment_detail_admin) {

    private val detailViewModel: DetailViewModel by viewModels()
    private val safeArgs: DetailAdminFragmentArgs by navArgs()
    private lateinit var detailAdapter: DetailAdapter
    private var type: String = ""
    private val mainViewModel: MainViewModel by viewModels()
    private var isFavorite: Boolean = false
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun initViews() {
        super.initViews()

        initBindings()
        setAdapter()
        onClickFavoritesButton()
        onClickBackButton()
        onClickSharedButton()
        onClickMoreButton()
    }

    override fun initObserves() {
        super.initObserves()

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

            if (detailRoom.result.comments.isEmpty()) {
                hideDetailRecyclerView()
            } else {
                hideNoReplyTextView()
                showDetailRecyclerView()
                reLoadComments(detailRoom.result.comments)
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
            if (comments.text != null) {
                showTextCommentBottomSheet(
                    comments.nickname,
                    comments.text,
                    comments.id,
                    comments.is_mine
                )
            } else {
                showVoiceCommentBottomSheet(comments.nickname, comments.id, comments.is_mine)
            }
        }

        binding.detailRecyclerView.adapter = detailAdapter
    }

    private fun reLoadComments(list: List<Comments>) {
        getNavigationResult<String>(R.id.detailAdminFragment, "comment") { result ->
            detailViewModel.getRoomDetailByRoomCode(safeArgs.roomCode)
            detailAdapter.submitList(list)
        }
    }

    private fun showTextCommentBottomSheet(
        name: String,
        content: String?,
        id: Long?,
        mine: Boolean
    ) {
        bottomSheetBehavior = BottomSheetBehavior.from(textCommentBottomSheet).apply {
            this.state = STATE_EXPANDED

            val nickName = requireActivity().findViewById<TextView>(R.id.nicknameTextView)
            val contents = requireActivity().findViewById<TextView>(R.id.contentsTextView)
            val closeImageView = requireActivity().findViewById<ImageView>(R.id.closeImageView)
            val report = requireActivity().findViewById<ImageView>(R.id.reportImageView)
            val remove = requireActivity().findViewById<ImageView>(R.id.removeImageView)
            nickName.text = name
            contents.text = content

            closeImageView.setOnClickListener { bottomSheetBehavior.state = STATE_HIDDEN }

            report.setOnClickListener {
                if (mine) requireActivity().showToast(
                    getString(R.string.dialog_report_mine),
                    requireView()
                )
                else showReportCommentDialog(id!!)
            }
            remove.setOnClickListener { showDeleteCommentDialog(id!!) }
        }
    }

    private fun showVoiceCommentBottomSheet(name: String, id: Long?, mine: Boolean) {
        bottomSheetBehavior = BottomSheetBehavior.from(voiceCommentBottomSheet).apply {
            this.state = STATE_EXPANDED

            val nickName = requireActivity().findViewById<TextView>(R.id.nicknameTextView)
            val closeImageView = requireActivity().findViewById<ImageView>(R.id.closeImageView)
            val report = requireActivity().findViewById<ImageView>(R.id.reportImageView)
            val remove = requireActivity().findViewById<ImageView>(R.id.removeImageView)
            nickName.text = name

            closeImageView.setOnClickListener { bottomSheetBehavior.state = STATE_HIDDEN }

            report.setOnClickListener {
                if (mine) requireActivity().showToast(
                    getString(R.string.dialog_report_mine),
                    requireView()
                )
                else showReportCommentDialog(id!!)
            }
            remove.setOnClickListener { showDeleteCommentDialog(id!!) }
        }
    }

    private fun showDeleteCommentDialog(id: Long) {
        val action =
            DetailAdminFragmentDirections.actionDetailAdminFragmentToCustomDialogFragment(
                "",
                id,
                R.layout.dialog_delete_reply_admin
            )
        findNavController().navigate(action)
    }

    private fun showReportCommentDialog(id: Long) {
        val action =
            DetailAdminFragmentDirections.actionDetailAdminFragmentToCustomDialogFragment(
                "",
                id,
                R.layout.dialog_report_reply
            )
        findNavController().navigate(action)
    }

    private fun startRecordVoiceDialogFragment() {
        binding.writeButton.setOnClickListener {
            val action =
                DetailAdminFragmentDirections.actionDetailAdminFragmentToRecordVoiceDialogFragment(
                    safeArgs.roomCode
                )
            findNavController().navigate(action)
        }
    }

    private fun startWriteTextDetailFragment() {
        binding.writeButton.setOnClickListener {
            val action =
                DetailAdminFragmentDirections.actionDetailAdminFragmentToWriteTextDialogFragment(
                    safeArgs.roomCode
                )
            findNavController().navigate(action)
        }
    }

    private fun onClickMoreButton() {
        binding.moreButton.setOnClickListener {
            val action =
                DetailAdminFragmentDirections.actionDetailAdminFragmentToDetailSharedBottomSheetFragment(
                    safeArgs.roomCode,
                    safeArgs.createdAt
                )
            findNavController().navigate(action)
        }
    }

    private fun onClickSharedButton() {
        binding.sharedLinkButton.setOnClickListener {
            val action =
                DetailAdminFragmentDirections.actionDetailAdminFragmentToDetailSharedBottomSheetFragment(
                    safeArgs.roomCode,
                    safeArgs.createdAt
                )
            findNavController().navigate(action)
            showNoReplyTextView()
        }
    }

    private fun onClickFavoritesButton() {
        binding.favoritesButton.isSelected = PreferenceManager(requireContext()).getFavorite()

        binding.favoritesButton.setOnClickListener {
            when (isFavorite) {
                true -> {
                    binding.favoritesButton.isSelected = false
                    isFavorite = false
                    PreferenceManager(requireContext()).setFavorite(false)
                    mainViewModel.deleteBookmarkByRoomCode(safeArgs.roomCode)
                }
                false -> {
                    binding.favoritesButton.isSelected = true
                    isFavorite = true
                    PreferenceManager(requireContext()).setFavorite(true)
                    mainViewModel.postBookmarkByRoomCode(safeArgs.roomCode)
                }
            }
        }
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
    }

    private fun showDetailRecyclerView() {
        binding.detailRecyclerView.isVisible = true
    }

    private fun hideDetailRecyclerView() {
        binding.detailRecyclerView.isVisible = false
    }

    private fun onClickBackButton() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}