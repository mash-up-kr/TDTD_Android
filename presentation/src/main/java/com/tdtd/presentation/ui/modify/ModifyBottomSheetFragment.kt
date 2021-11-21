package com.tdtd.presentation.ui.modify

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.tdtd.domain.entity.ModifyRoomNameEntity
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.FragmentModifyBottomSheetBinding
import com.tdtd.presentation.ui.detail.DetailViewModel
import com.tdtd.presentation.utils.onThrottleClick
import com.tdtd.presentation.utils.setNavigationResult

class ModifyBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentModifyBottomSheetBinding
    private val detailViewModel: DetailViewModel by activityViewModels()
    private val safeArgs: ModifyBottomSheetFragmentArgs by navArgs()
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var newRoomTitle = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_modify_bottom_sheet,
                container,
                false
            )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAnalytics()
        setModifyRoomNameEdit()
    }

    private fun initAnalytics() {
        firebaseAnalytics = Firebase.analytics
    }

    private fun setModifyRoomNameEdit() {
        binding.modifyRoomNameEditView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textNumberTextView.text =
                    getString(R.string.initial_and_max_input_number, s?.length)

                newRoomTitle = s.toString()

                when (newRoomTitle.isNotEmpty() && newRoomTitle.isNotBlank()) {
                    true -> onClickModifyButton()
                    false -> emptyModifyRoomNameEdit()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun onClickModifyButton() {
        binding.apply {
            modifyRoomNameButton.isEnabled = true
            modifyRoomNameButton.setBackgroundResource(R.drawable.backgroud_grayscale1_radius12_click)
        }

        binding.modifyRoomNameButton.onThrottleClick {
            detailViewModel.modifyRoomNameByHost(
                safeArgs.roomCode,
                ModifyRoomNameEntity(newRoomTitle)
            )
            setNavigationResult(newRoomTitle, "modify_room_name")
            dismiss()
        }.also {
            val bundle = Bundle()
            bundle.putString("value", "roomTitle")
            firebaseAnalytics.logEvent("ModifyTitle", bundle)
        }
    }

    private fun emptyModifyRoomNameEdit() {
        binding.apply {
            modifyRoomNameButton.isEnabled = false
            modifyRoomNameButton.setBackgroundResource(R.drawable.background_grayscale1_radius12)
        }
    }
}