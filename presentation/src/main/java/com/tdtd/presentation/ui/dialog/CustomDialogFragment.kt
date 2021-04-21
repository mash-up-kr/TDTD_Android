package com.tdtd.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_leave_room.view.*

class CustomDialogFragment(private val dialogLayoutId: Int) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = inflater.inflate(dialogLayoutId, container, false)

        view.apply {
            submitButton.setOnClickListener {
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