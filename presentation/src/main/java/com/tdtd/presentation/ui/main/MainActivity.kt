package com.tdtd.presentation.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.ActivityMainBinding
import com.tdtd.presentation.entity.Dummy
import com.tdtd.presentation.entity.getData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dummyList: List<Dummy> = getData()
    private var mainAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        // TEST
        mainAdapter.submitList(dummyList)
        binding.recyclerView.adapter = mainAdapter
        binding.recyclerView.adapter?.notifyDataSetChanged()

    }
}
