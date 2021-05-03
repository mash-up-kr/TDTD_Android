package com.tdtd.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.tdtd.presentation.R
import com.tdtd.presentation.ui.detail.DetailViewModel
import com.tdtd.presentation.util.setNavigationResult
import kotlinx.android.synthetic.main.dialog_leave_room.view.*

class CustomDialogFragment(private val dialogLayoutId: Int) : DialogFragment() {

    private val detailViewModel: DetailViewModel by viewModels({ requireParentFragment() })
    private val roomCode by lazy { requireArguments().getString("roomCode") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = inflater.inflate(dialogLayoutId, container, false)

        view.apply {
            submitButton.setOnClickListener {
                setNavigationResult(getString(R.string.toast_leave_room_success), "detail").also {
                    detailViewModel.deleteParticipatedUserRoom(roomCode!!)
                }
                requireActivity().onBackPressed()
                dismiss()
            }
            cancelButton.setOnClickListener {
                dismiss()
            }
        }

        return view
    }
}