package com.tdtd.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tdtd.presentation.R
import com.tdtd.presentation.ui.detail.DetailViewModel
import com.tdtd.presentation.util.setNavigationResult
import kotlinx.android.synthetic.main.dialog_leave_room.view.*

class CustomDialogFragment : DialogFragment() {

    private val detailViewModel: DetailViewModel by activityViewModels()
    private val safeArgs: CustomDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = inflater.inflate(safeArgs.layoutId, container, false)

        view.apply {
            when (safeArgs.layoutId) {
                R.layout.dialog_leave_room -> {
                    submitButton.setOnClickListener {
                        setNavigationResult(
                            getString(R.string.toast_leave_room_success),
                            "detail"
                        ).also {
                            detailViewModel.deleteParticipatedUserRoom(safeArgs.roomCode)
                            dismiss()
                        }
                    }
                }
                R.layout.dialog_report_reply -> {
                    submitButton.setOnClickListener {
                        detailViewModel.postReportUserByCommentId(safeArgs.id)
                        dismiss()
                    }
                }
                R.layout.dialog_delete_reply -> {
                    submitButton.setOnClickListener {
                        detailViewModel.deleteReplyUserComment(safeArgs.id).apply {
                            deleteComment()
                            dialog?.dismiss()
                        }
                    }
                }
                R.layout.dialog_delete_reply_admin -> {
                    submitButton.setOnClickListener {
                        detailViewModel.deleteOtherCommentByAdmin(safeArgs.id).apply {
                            deleteCommentByAdmin()
                            dialog?.dismiss()
                        }
                    }
                }
                R.layout.dialog_delete_room -> {
                    submitButton.setOnClickListener {
                        setNavigationResult(
                            getString(R.string.toast_delete_room_success),
                            "detail"
                        ).also {
                            detailViewModel.deleteParticipatedUserRoom(safeArgs.roomCode)
                            dismiss()
                        }
                    }
                }
                R.layout.dialog_share_room -> {
                    cancelButton.setOnClickListener { dismiss() }
                }
            }
            cancelButton.setOnClickListener { dismiss() }
        }

        return view
    }

    private fun deleteCommentByAdmin() {
        val action = CustomDialogFragmentDirections.actionCustomDialogFragmentToDetailFragment(
            safeArgs.roomCode,
            "",
            true,
            safeArgs.bookmark
        )
        findNavController().navigate(action)
        findNavController().popBackStack()
    }

    private fun deleteComment() {
        val action =
            CustomDialogFragmentDirections.actionCustomDialogFragmentToDetailFragment(
                safeArgs.roomCode,
                "",
                false,
                safeArgs.bookmark
            )
        findNavController().navigate(action)
        findNavController().popBackStack()
    }
}