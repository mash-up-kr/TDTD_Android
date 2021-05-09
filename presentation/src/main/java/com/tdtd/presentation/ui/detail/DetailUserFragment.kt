package com.tdtd.presentation.ui.detail

import android.widget.ImageView
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
import com.tdtd.presentation.databinding.FragmentDetailUserBinding
import com.tdtd.presentation.entity.Comments
import com.tdtd.presentation.ui.main.MainViewModel
import com.tdtd.presentation.util.PreferenceManager
import com.tdtd.presentation.util.getNavigationResultLiveData
import com.tdtd.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_text_comment.*
import kotlinx.android.synthetic.main.fragment_voice_comment.*

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
                showEmptyDetailRoom()
                hideDetailRecyclerView()
            } else {
                showDetailRecyclerView()
                hideEmptyDetailRoom()
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
        getNavigationResultLiveData<String>("comment")?.observe(viewLifecycleOwner) {
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
                if (!mine) showDeleteCommentDialog(id!!)
                else requireActivity().showToast(
                    getString(R.string.dialog_delete_mine),
                    requireView()
                )
            }
        }
    }

    private fun showVoiceCommentBottomSheet(name: String, id: Long?, mine: Boolean) {
        bottomSheetBehavior = BottomSheetBehavior.from(voiceCommentBottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_EXPANDED

            val nickName = requireActivity().findViewById<TextView>(R.id.nicknameTextView)
            val closeImageView = requireActivity().findViewById<ImageView>(R.id.closeImageView)
            val report = requireActivity().findViewById<ImageView>(R.id.reportImageView)
            val remove = requireActivity().findViewById<ImageView>(R.id.removeImageView)
            nickName.text = name

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
                if (!mine) showDeleteCommentDialog(id!!)
                else requireActivity().showToast(
                    getString(R.string.dialog_delete_mine),
                    requireView()
                )
            }
        }
    }

    private fun showDeleteCommentDialog(id: Long) {
        val action =
            DetailUserFragmentDirections.actionDetailUserFragmentToCustomDialogFragment(
                "",
                id,
                R.layout.dialog_delete_reply
            )
        findNavController().navigate(action)
    }

    private fun showReportCommentDialog(id: Long) {
        val action =
            DetailUserFragmentDirections.actionDetailUserFragmentToCustomDialogFragment(
                "",
                id,
                R.layout.dialog_report_reply
            )
        findNavController().navigate(action)
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
            val action =
                DetailUserFragmentDirections.actionDetailUserFragmentToCustomDialogFragment(
                    safeArgs.roomCode,
                    0,
                    R.layout.dialog_leave_room
                )
            findNavController().navigate(action)
        }
    }
}