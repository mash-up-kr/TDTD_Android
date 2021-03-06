package com.tdtd.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.ActivityDetailAdminBinding
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
        onClickFavoritesButton()
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
}