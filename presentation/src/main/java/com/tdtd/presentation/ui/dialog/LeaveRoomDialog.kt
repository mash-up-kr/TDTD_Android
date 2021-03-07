package com.tdtd.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tdtd.presentation.R
import kotlinx.android.synthetic.main.dialog_leave_room.view.*

class LeaveRoomDialog() : DialogFragment(),
        View.OnClickListener {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_leave_room, container)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.apply {
            submitButton.setOnClickListener {
                submitButtonClicked(true)
                dismiss()
            }
            cancelButton.setOnClickListener {
                dismiss()
            }
        }
        return view
    }

    companion object {
        lateinit var submitButtonClicked: (Boolean) -> Unit
        fun getInstance(submitButtonClicked: (Boolean) -> Unit): LeaveRoomDialog {
            this.submitButtonClicked = submitButtonClicked
            return LeaveRoomDialog()
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }
}