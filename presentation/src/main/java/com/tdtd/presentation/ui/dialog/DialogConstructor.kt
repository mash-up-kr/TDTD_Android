package com.tdtd.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_leave_room.view.*

class DialogConstructor(private val dialogLayout: Int) : DialogFragment(),
        View.OnClickListener {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(dialogLayout, container)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.apply {
            submitButton.setOnClickListener {
                dismiss()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            cancelButton.setOnClickListener {
                dismiss()
            }
        }
        return view
    }

    companion object {
        lateinit var submitButtonClicked: (Boolean) -> Unit
        fun getInstance(submitButtonClicked: (Boolean) -> Unit, dialogLayout: Int): DialogConstructor {
            this.submitButtonClicked = submitButtonClicked
            return DialogConstructor(dialogLayout)
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }
}