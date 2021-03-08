package com.tdtd.presentation.ui.main

import androidx.fragment.app.DialogFragment
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseActivity
import com.tdtd.presentation.databinding.ActivityMainBinding
import com.tdtd.presentation.entity.Dummy
import com.tdtd.presentation.entity.getData
import com.tdtd.presentation.ui.makeroom.RoomDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val dummyList: List<Dummy> = getData()
    private var mainAdapter = MainAdapter()

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
    }

    private fun initRoomDialogFragment() {
        val bottomSheet = RoomDialogFragment()
        bottomSheet.run {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
            show(supportFragmentManager, bottomSheet.tag)
        }
    }
}
