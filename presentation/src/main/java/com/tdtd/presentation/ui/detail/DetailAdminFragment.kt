package com.tdtd.presentation.ui.detail

import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.tdtd.domain.entity.MakeRoomType
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentDetailAdminBinding
import com.tdtd.presentation.ui.main.MainViewModel
import com.tdtd.presentation.ui.reply.RecordVoiceDialogFragment
import com.tdtd.presentation.ui.reply.WriteTextDialogFragment
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
        onClickMoreButton()
    }

    override fun initObserves() {
        super.initObserves()

        detailViewModel.detailRoom.observe(viewLifecycleOwner, Observer { detailRoom ->
            binding.titleTextView.text = detailRoom.result.title

            if (detailRoom.result.comments.isEmpty()) {
                onClickSharedButton()
                hideDetailRecyclerView()
            } else {
                hideNoReplyTextView()
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
                else detailViewModel.postReportUserByCommentId(id!!)
                    .apply { bottomSheetBehavior.state = STATE_HIDDEN }
            }
            remove.setOnClickListener {
                if (!mine) detailViewModel.deleteOtherCommentByAdmin(id!!)
                    .apply { bottomSheetBehavior.state = STATE_HIDDEN }
                else requireActivity().showToast(
                    getString(R.string.dialog_delete_mine),
                    requireView()
                )
            }
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
                else detailViewModel.postReportUserByCommentId(id!!)
                    .apply { bottomSheetBehavior.state = STATE_HIDDEN }
            }
            remove.setOnClickListener {
                if (!mine) detailViewModel.deleteOtherCommentByAdmin(id!!)
                    .apply { bottomSheetBehavior.state = STATE_HIDDEN }
                else requireActivity().showToast(
                    getString(R.string.dialog_delete_mine),
                    requireView()
                )
            }
        }
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

    private fun onClickMoreButton() {
        binding.moreButton.setOnClickListener {
            val dialog = DetailSharedBottomSheetFragment()
            dialog.arguments =
                bundleOf("roomCode" to safeArgs.roomCode, "date" to safeArgs.createdAt)
            dialog.show(childFragmentManager, dialog.tag)
        }
    }

    private fun onClickSharedButton() {
        binding.sharedLinkButton.setOnClickListener {
            val dialog = DetailSharedBottomSheetFragment()
            dialog.arguments =
                bundleOf("roomCode" to safeArgs.roomCode, "date" to safeArgs.createdAt)
            dialog.show(childFragmentManager, dialog.tag)

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