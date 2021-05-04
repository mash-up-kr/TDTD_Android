package com.tdtd.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.DetailAdminBottomSheetBinding
import com.tdtd.presentation.ui.dialog.CustomDialogFragment
import com.tdtd.presentation.util.initParentHeight

class DetailSharedBottomSheetFragment : BottomSheetDialogFragment() {

    private val detailViewModel: DetailViewModel by viewModels({ requireParentFragment() })
    private val roomCode by lazy { requireArguments().getString("roomCode") }
    private val date by lazy { requireArguments().getString("date") }
    private lateinit var binding: DetailAdminBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.detail_admin_bottom_sheet, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initParentHeight(requireActivity(), view, 420)
        initDate()
        onClickCancelButton()
        onClickSharedLink()
        onClickDeleteRoomTextView()
    }

    private fun initDate() {
        val parse = date?.substring(0, 10)

        binding.dateInfoTitleTextView.text =
            getString(R.string.detail_admin_setting_bottom_sheet_title, parse)
    }

    private fun onClickDeleteRoomTextView() {
        binding.deleteRoomTextView.setOnClickListener {
            showDeleteRoomDialog()
        }
    }

    private fun onClickCancelButton() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun onClickSharedLink() {
        binding.sharedLinkTextView.setOnClickListener {
            detailViewModel.getSharedRoomUrl(roomCode!!).apply {
                showSharedRoomDialog()
            }
        }
    }

    private fun showSharedRoomDialog() {
        val dialog = CustomDialogFragment(R.layout.dialog_share_room)
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun showDeleteRoomDialog() {
        val dialog = CustomDialogFragment(R.layout.dialog_delete_room)
        dialog.arguments = bundleOf("roomCode" to roomCode)
        dialog.show(childFragmentManager, dialog.tag)
    }
}