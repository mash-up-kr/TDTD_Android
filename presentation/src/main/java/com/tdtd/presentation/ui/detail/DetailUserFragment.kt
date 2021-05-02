package com.tdtd.presentation.ui.detail

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tdtd.domain.entity.MakeRoomType
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentDetailUserBinding
import com.tdtd.presentation.ui.dialog.CustomDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserFragment : BaseFragment<FragmentDetailUserBinding>(R.layout.fragment_detail_user) {

    private val detailViewModel: DetailViewModel by viewModels()
    private val safeArgs: DetailUserFragmentArgs by navArgs()
    private lateinit var detailAdapter: DetailAdapter

    companion object {
        var currentPosition: Int = 0
        var prevPosition: Int = 0
    }

    /*private var detailAdapter = DetailAdapter() { position ->
        // TODO: companion object를 포함하여 테스트 용으로 추가한 코드라 수정해야 합니다. 스크롤하면서 터치하면 앱 죽어요.....
        prevPosition = currentPosition
        currentPosition = position
        recyclerView.get(prevPosition).characterImageView.setImageResource(
            getDefaultCharacter(
                contentList.get(prevPosition).presenterSticker_color
            )
        )
        recyclerView.get(currentPosition).characterImageView.setImageResource(
            getSelectedCharacter(
                contentList.get(currentPosition).presenterSticker_color
            )
        )
    }
*/
    override fun initViews() {
        super.initViews()

        val inflater = requireActivity().getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // TODO: contents 유무에 따라 view가 달라져야 합니다.
        val view = inflater.inflate(
            R.layout.layout_detail_room_contents,
            binding.detailUserFrameLayout,
            false
        )
        binding.detailUserFrameLayout.addView(view)

        initBindings()
        setAdapter()
        onClickFavoritesButton()
        onClickBackButton()
        onClickLeaveRoomButton()
    }

    override fun initObserves() {
        super.initObserves()

        detailViewModel.detailRoom.observe(viewLifecycleOwner, Observer { detailRoom ->
            if (detailRoom.result.comments.isEmpty()) {
                showEmptyDetailRoom()
                hideDetailRecyclerView()
            } else {
                showDetailRecyclerView()
                hideEmptyDetailRoom()
            }

            when (detailRoom.result.type) {
                MakeRoomType.TEXT -> {
                    startWriteTextDetailFragment()
                }
                MakeRoomType.VOICE -> {
                    startRecordVoiceDialogFragment()
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
        detailAdapter = DetailAdapter {

        }

        binding.detailRecyclerView.adapter = detailAdapter
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
        binding.writeButton.setOnClickListener { findNavController().navigate(R.id.action_detailUserFragment_to_recordVoiceDialogFragment) }
    }

    private fun startWriteTextDetailFragment() {
        binding.writeButton.setOnClickListener { findNavController().navigate(R.id.action_detailUserFragment_to_writeTextDialogFragment) }
    }

    private fun onClickFavoritesButton() {
        binding.favoritesButton.setOnClickListener {
            binding.favoritesButton.isSelected = !binding.favoritesButton.isSelected
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