package com.tdtd.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.DetailAdminBottomSheetBinding
import com.tdtd.presentation.ui.dialog.CustomDialogFragment
import com.tdtd.presentation.util.setNavigationResult

class DetailAdminDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DetailAdminBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.detail_admin_bottom_sheet, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickSharedLink()
        onClickDeleteRoom()
        onClickCancelButton()
    }

    private fun onClickSharedLink() {
        binding.sharedLinkTextView.setOnClickListener {
            // TODO: 링크가 복사되었어요! 와 같은 Toast 출력? 디자인 파트와 논의 필요
        }
    }

    private fun onClickDeleteRoom() {
        binding.deleteRoomTextView.setOnClickListener {
            setNavigationResult(getString(R.string.dialog_delete_room_button_submit))
            val dialog = CustomDialogFragment(R.layout.dialog_delete_room)
            dialog.show(requireActivity().supportFragmentManager, dialog.tag)
            // TODO: 방 삭제 submit button click시 Activity 종료 + 토스트 출력 + API 호출
        }
    }

    private fun onClickCancelButton() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}