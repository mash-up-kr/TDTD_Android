package com.tdtd.presentation.ui.detail

import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseActivity
import com.tdtd.presentation.databinding.ActivityDetailAdminBinding
import com.tdtd.presentation.ui.recordvoice.RecordVoiceDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAdminActivity : BaseActivity<ActivityDetailAdminBinding>(R.layout.activity_detail_admin) {

    override fun initViews() {
        super.initViews()

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_detail_admin, binding.detailAdminFrameLayout, false)
        binding.detailAdminFrameLayout.addView(view)

        onClickWriteButton()
        onClickFavoritesButton()
        onClickBackButton()
        onClickMoreButton()
    }

    private fun onClickFavoritesButton() {
        binding.favoritesButton.setOnClickListener {
            binding.favoritesButton.isSelected = !binding.favoritesButton.isSelected
        }
    }

    private fun onClickWriteButton() {
        binding.writeButton.setOnClickListener {
            initVoiceRecordDialogFragment()
        }
    }

    private fun onClickBackButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun onClickMoreButton() {
        binding.moreButton.setOnClickListener {
            initDetailAdminDialogFragment()
        }
    }

    private fun initVoiceRecordDialogFragment() {
        val bottomSheet = RecordVoiceDialogFragment()
        bottomSheet.run {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
            show(supportFragmentManager, bottomSheet.tag)
        }
    }

    private fun initDetailAdminDialogFragment() {
        val bottomSheet = DetailAdminDialogFragment()
        bottomSheet.run {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.DetailAdminBottomSheetModelTheme)
            show(supportFragmentManager, bottomSheet.tag)
        }
    }
}