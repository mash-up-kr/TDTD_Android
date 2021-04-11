package com.tdtd.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.ActivityDetailAdminBinding
import com.tdtd.presentation.ui.recordvoice.RecordVoiceDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAdminActivity : AppCompatActivity() {

    private lateinit var inflater: LayoutInflater
    private lateinit var view: View

    private lateinit var binding: ActivityDetailAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_admin)

        initView()
        onClickWriteButton()
        onClickFavoritesButton()
        onClickMoreButton()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_admin)
        binding.lifecycleOwner = this

        inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        view = inflater.inflate(R.layout.layout_detail_admin, binding.detailAdminFrameLayout, false)
        binding.detailAdminFrameLayout.addView(view)
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