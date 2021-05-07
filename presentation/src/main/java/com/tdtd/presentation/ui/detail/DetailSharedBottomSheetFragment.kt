package com.tdtd.presentation.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.DetailAdminBottomSheetBinding
import com.tdtd.presentation.ui.dialog.CustomDialogFragment

class DetailSharedBottomSheetFragment : BottomSheetDialogFragment() {

    private val detailViewModel: DetailViewModel by viewModels({ requireParentFragment() })
    private val roomCode by lazy { requireArguments().getString("roomCode") }
    private val roomDate by lazy { requireArguments().getString("date") }
    private lateinit var binding: DetailAdminBottomSheetBinding
    private var sharedUrl: String = ""
    private var list = listOf<String>()

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

        initDate()
        onClickCancelButton()
        onClickSharedLink()
        onClickDeleteRoomTextView()
    }

    private fun initDate() {
        if (!roomDate.equals(getString(R.string.make_new_room))) {
            list = roomDate!!.substring(0, 10).split("-")
            val year = list[0].plus("년")
            val month = list[1].plus("월")
            val day = list[2].plus("일")
            val date = "$year $month $day"

            binding.dateInfoTitleTextView.text =
                getString(R.string.detail_admin_setting_bottom_sheet_title, date)
        } else binding.dateInfoTitleTextView.text = getString(
            R.string.detail_admin_setting_bottom_sheet_title, getString(R.string.make_new_room)
        )
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
            }.also {
                observeSharedUrl()
            }
        }
    }

    private fun observeSharedUrl() {
        detailViewModel.sharedUrl.observe(viewLifecycleOwner, Observer {
            sharedUrl = it.result.shareUrl
            copySharedUrlToClipboard(sharedUrl)
        })
    }

    private fun copySharedUrlToClipboard(url: String) {
        val clipboardManager =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Shared Url", url)
        clipboardManager.setPrimaryClip(clip)
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