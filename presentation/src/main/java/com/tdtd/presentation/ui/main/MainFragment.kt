package com.tdtd.presentation.ui.main

import androidx.navigation.fragment.findNavController
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentMainBinding
import com.tdtd.presentation.entity.Room
import com.tdtd.presentation.entity.getRooms
import com.tdtd.presentation.util.getNavigationResult
import com.tdtd.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val roomList: List<Room> = getRooms()

    private var mainAdapter = MainAdapter() { position ->
        if (position == 0) {
            startDetailAdminFragment()
        } else {
            startDetailUserFragment()
        }
    }

    override fun initViews() {
        super.initViews()

        getNavigationResult()?.observe(viewLifecycleOwner) { result ->
            requireActivity().showToast(result, requireView())
        }
        // TEST
        mainAdapter.submitList(roomList)
        binding.recyclerView.adapter = mainAdapter

        onClickAddImageView()
    }

    private fun startDetailAdminFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_detailAdminFragment)
    }

    private fun startDetailUserFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_detailUserFragment)
    }

    private fun onClickAddImageView() {
        binding.rollingPaperAddImageView.setOnClickListener {
            initRoomDialogFragment()
        }

        binding.settingButton.setOnClickListener {
            requireActivity().showToast("테스트입니다!", it)
        }
    }

    private fun initRoomDialogFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_roomDialogFragment)
    }
}