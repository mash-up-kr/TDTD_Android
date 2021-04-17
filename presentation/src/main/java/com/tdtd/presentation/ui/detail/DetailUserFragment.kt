package com.tdtd.presentation.ui.detail

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentDetailUserBinding
import com.tdtd.presentation.entity.Comments
import com.tdtd.presentation.entity.getComments
import com.tdtd.presentation.entity.getDefaultCharacter
import com.tdtd.presentation.entity.getSelectedCharacter
import com.tdtd.presentation.ui.dialog.DialogConstructor
import com.tdtd.presentation.util.setNavigationResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_detail_room_contents.*
import kotlinx.android.synthetic.main.row_detail_items.view.*

@AndroidEntryPoint
class DetailUserFragment : BaseFragment<FragmentDetailUserBinding>(R.layout.fragment_detail_user) {

    private val contentList: List<Comments> = getComments()

    companion object {
        var currentPosition: Int = 0
        var prevPosition: Int = 0
    }

    private var detailAdapter = DetailAdapter() { position ->
        // TODO: companion object를 포함하여 테스트 용으로 추가한 코드라 수정해야 합니다. 스크롤하면서 터치하면 앱 죽어요.....
        prevPosition = currentPosition
        currentPosition = position
        recyclerView.get(prevPosition).characterImageView.setImageResource(
            getDefaultCharacter(
                contentList.get(prevPosition).sticker_color
            )
        )
        recyclerView.get(currentPosition).characterImageView.setImageResource(
            getSelectedCharacter(
                contentList.get(currentPosition).sticker_color
            )
        )
    }

    override fun initViews() {
        super.initViews()

        val inflater = requireActivity().getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // TODO: contents 유무에 따라 view가 달라져야 합니다.
        val view = inflater.inflate(
            R.layout.layout_detail_room_contents,
            binding.detailUserFrameLayout,
            false
        )
//        view = inflater.inflate(R.layout.layout_detail_user, binding.detailUserFrameLayout, false)
        binding.detailUserFrameLayout.addView(view)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        detailAdapter.submitList(contentList)
        recyclerView.adapter = detailAdapter

        onClickFavoritesButton()
        onClickBackButton()
        onClickLeaveRoomButton()
    }

    private fun onClickFavoritesButton() {
        binding.favoritesButton.setOnClickListener {
            binding.favoritesButton.isSelected = !binding.favoritesButton.isSelected
        }
    }


    private fun onClickBackButton() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun onClickLeaveRoomButton() {
        binding.leaveRoomButton.setOnClickListener {
            setNavigationResult(getString(R.string.toast_leave_room_success))
            val dialog = DialogConstructor(R.layout.dialog_leave_room)
            dialog.show(requireActivity().supportFragmentManager, dialog.tag)
        }
    }
}