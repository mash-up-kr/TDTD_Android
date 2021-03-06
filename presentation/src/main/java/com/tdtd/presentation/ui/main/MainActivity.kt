package com.tdtd.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseActivity
import com.tdtd.presentation.databinding.ActivityMainBinding
import com.tdtd.presentation.entity.Dummy
import com.tdtd.presentation.entity.getData
import com.tdtd.presentation.ui.detail.DetailAdminActivity
import com.tdtd.presentation.ui.makeroom.RoomDialogFragment
import com.tdtd.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val dummyList: List<Dummy> = getData()
    private var mainAdapter = MainAdapter() { position ->
        startActivity(Intent(this, DetailAdminActivity::class.java))
    }

    override fun initViews() {
        super.initViews()

        // TEST
        mainAdapter.submitList(dummyList)
        binding.recyclerView.adapter = mainAdapter
        binding.recyclerView.adapter?.notifyDataSetChanged()

        onClickAddImageView()
    }


    private fun onClickAddImageView() {
        binding.rollingPaperAddImageView.setOnClickListener {
            initRoomDialogFragment()
        }

        binding.settingButton.setOnClickListener {
             this@MainActivity.showToast("테스트입니다!", it)
        }
    }

    private fun initRoomDialogFragment() {
        val bottomSheet = RoomDialogFragment()
        bottomSheet.run {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
            show(supportFragmentManager, bottomSheet.tag)
        }
    }
}
