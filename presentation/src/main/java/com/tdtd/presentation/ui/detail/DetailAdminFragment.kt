package com.tdtd.presentation.ui.detail

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentDetailAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAdminFragment : BaseFragment<FragmentDetailAdminBinding>(R.layout.fragment_detail_admin) {

    override fun initViews() {
        super.initViews()

        val inflater = requireActivity().getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_detail_admin, binding.detailAdminFrameLayout, false)
        binding.detailAdminFrameLayout.addView(view)

        onClickFavoritesButton()
        onClickBackButton()
    }

    private fun onClickFavoritesButton() {
        binding.favoritesButton.setOnClickListener {
            binding.favoritesButton.isSelected = !binding.favoritesButton.isSelected
        }
    }

    private fun onClickBackButton() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}