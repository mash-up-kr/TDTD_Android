package com.tdtd.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.tdtd.presentation.R
import com.tdtd.presentation.ui.detail.DetailViewModel
import com.tdtd.presentation.util.setNavigationResult
import kotlinx.android.synthetic.main.dialog_leave_room.view.*

class CustomDialogFragment : DialogFragment() {

    private val detailViewModel: DetailViewModel by activityViewModels()
    private val roomCode by lazy { requireArguments().getString("roomCode") }
    private val commentId by lazy { requireArguments().getLong("id") }
    private val safeArgs : CustomDialogFragmentArgs by navArgs()

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
                            detailViewModel.deleteParticipatedUserRoom(roomCode!!)
                        }
                        requireActivity().onBackPressed()
                        dismiss()
                    }
                }
                R.layout.dialog_report_reply -> {
                    submitButton.setOnClickListener {
                        detailViewModel.postReportUserByCommentId(commentId)
                        dismiss()
                    }
                }
                R.layout.dialog_delete_reply -> {
                    submitButton.setOnClickListener {
                        detailViewModel.deleteReplyUserComment(commentId)
                        dismiss()
                    }
                }
                R.layout.dialog_delete_room -> {
                    submitButton.setOnClickListener {
                        detailViewModel.deleteParticipatedUserRoom(safeArgs.roomCode)
                        requireActivity().onBackPressed()
                        dismiss()
                    }
                }
                R.layout.dialog_share_room -> {
                    cancelButton.setOnClickListener { dismiss() }
                }
            }

            cancelButton.setOnClickListener {
                dismiss()
            }
        }

        return view
    }
}