package com.tdtd.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.ui.dialog.CustomDialogFragment
import com.tdtd.presentation.util.initParentHeight
import com.tdtd.presentation.util.showToast
import kotlinx.android.synthetic.main.fragment_text_comment.view.*

class DetailCommentBottomSheetFragment(private val layoutId: Int) : BottomSheetDialogFragment() {

    private val detailViewModel: DetailViewModel by viewModels({ requireParentFragment() })
    private val nickName by lazy { requireArguments().getString("nickName") }
    private val contents by lazy { requireArguments().getString("contents") }
    private val id by lazy { requireArguments().getLong("id") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initParentHeight(requireActivity(), view, 410)
        observeCommentReport()
        observeCommentDelete()

        view.apply {
            when (layoutId) {
                R.layout.fragment_text_comment -> {
                    nicknameTextView.text = nickName
                    contentsTextView.text = contents
                }
                R.layout.fragment_voice_comment -> {
                    nicknameTextView.text = nickName
                    // voice file
                }
            }

            removeImageView.setOnClickListener {
                showDeleteCommentDialog()
            }
            reportImageView.setOnClickListener {
                showReportCommentDialog()
            }

            closeImageView.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun observeCommentReport() {
        detailViewModel.alreadyReportEvent.observe(viewLifecycleOwner, Observer {
            requireActivity().showToast(
                getString(R.string.dialog_report_reduplicate),
                requireView()
            )
        })
    }

    private fun observeCommentDelete() {
        detailViewModel.notMineEvent.observe(viewLifecycleOwner, Observer {
            requireActivity().showToast(getString(R.string.dialog_delete_not_mine), requireView())
        })
    }

    private fun showDeleteCommentDialog() {
        val dialog = CustomDialogFragment(R.layout.dialog_delete_reply)
        dialog.arguments = bundleOf("id" to id)
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun showReportCommentDialog() {
        val dialog = CustomDialogFragment(R.layout.dialog_report_reply)
        dialog.arguments = bundleOf("id" to id)
        dialog.show(childFragmentManager, dialog.tag)
    }
}