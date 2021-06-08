package com.tdtd.presentation.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.DetailAdminBottomSheetBinding
import com.tdtd.presentation.util.getNavigationResult
import com.tdtd.presentation.util.onThrottleClick
import com.tdtd.presentation.util.showToast

class DetailSharedBottomSheetFragment : BottomSheetDialogFragment() {

    private val detailViewModel: DetailViewModel by activityViewModels()
    private val safeArgs: DetailSharedBottomSheetFragmentArgs by navArgs()
    private lateinit var binding: DetailAdminBottomSheetBinding
    private var sharedUrl: String = ""
    private var list = listOf<String>()
    private lateinit var firebaseAnalytics: FirebaseAnalytics

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
        initAnalytics()
        onClickCancelButton()
        onClickSharedLink()
        onClickDeleteRoomTextView()
        deleteRoom()
    }

    private fun initAnalytics() {
        firebaseAnalytics = Firebase.analytics
    }

    private fun deleteRoom() {
        getNavigationResult<String>(R.id.detailSharedBottomSheetFragment, "detail") {
            findNavController().navigateUp()
            findNavController().popBackStack()
            detailViewModel.deleteRoom(it)
        }

        detailViewModel.deleteRoomValue.observe(viewLifecycleOwner) { text ->
            if (text.isNotEmpty()) {
                requireActivity().showToast(
                    getString(R.string.toast_delete_room_success),
                    requireView()
                )
            }
        }
    }

    private fun initDate() {
        if (safeArgs.createdAt != getString(R.string.make_new_room)) {
            list = safeArgs.createdAt.substring(0, 10).split("-")
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
        binding.deleteRoomTextView.onThrottleClick {
            showDeleteRoomDialog()
        }
    }

    private fun onClickCancelButton() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun onClickSharedLink() {
        binding.sharedLinkTextView.onThrottleClick {
            detailViewModel.getSharedRoomUrl(safeArgs.roomCode).apply {
                showSharedRoomDialog()
            }.also {
                observeSharedUrl()
                val bundle = Bundle()
                bundle.putString("value", "copy")
                firebaseAnalytics.logEvent("CopyLink", bundle)
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
        val action =
            DetailSharedBottomSheetFragmentDirections.actionDetailSharedBottomSheetFragmentToCustomDialogFragment(
                safeArgs.roomCode,
                0,
                R.layout.dialog_share_room,
                safeArgs.bookmark,
                false
            )
        findNavController().navigate(action)
    }

    private fun showDeleteRoomDialog() {
        val action =
            DetailSharedBottomSheetFragmentDirections.actionDetailSharedBottomSheetFragmentToCustomDialogFragment(
                safeArgs.roomCode,
                0,
                R.layout.dialog_delete_room,
                safeArgs.bookmark,
                false
            )
        findNavController().navigate(action)
    }
}