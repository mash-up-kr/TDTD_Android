package com.tdtd.presentation.ui.main

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseActivity
import com.tdtd.presentation.databinding.ActivityMainBinding
import com.tdtd.presentation.entity.Dummy
import com.tdtd.presentation.entity.getData
import com.tdtd.presentation.ui.detail.DetailAdminActivity
import com.tdtd.presentation.ui.detail.DetailUserActivity
import com.tdtd.presentation.ui.makeroom.RoomDialogFragment
import com.tdtd.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val dummyList: List<Dummy> = getData()

    val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val isLeaveRoom = result.data?.getBooleanExtra("isLeaveRoom", false)
            if (isLeaveRoom == true) {
                this@MainActivity.showToast(getString(R.string.toast_leave_room_success), View(applicationContext))
            }
        }
    }

    private var mainAdapter = MainAdapter() { position ->
        if (position == 0) {
            startDetailAdminActivity()
        } else {
            startDetailUserActivity()
        }
    }

    override fun initViews() {
        super.initViews()

        // TEST
        mainAdapter.submitList(dummyList)
        binding.recyclerView.adapter = mainAdapter
        binding.recyclerView.adapter?.notifyDataSetChanged()

        onClickAddImageView()
    }

    private fun startDetailAdminActivity() {
        val intent = Intent(this, DetailAdminActivity::class.java)
        startActivityForResult.launch(intent)
    }

    private fun startDetailUserActivity() {
        val intent = Intent(this, DetailUserActivity::class.java)
        startActivityForResult.launch(intent)
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
