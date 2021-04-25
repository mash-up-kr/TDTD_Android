package com.tdtd.presentation.ui.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentMainBinding
import com.tdtd.presentation.util.getNavigationResult
import com.tdtd.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()

    private var mainAdapter = MainAdapter() { position ->
        if (position == 0) {
            startDetailAdminFragment()
        } else {
            startDetailUserFragment()
        }
    }

    override fun initViews() {
        super.initViews()

        getNavigationResult<String>(R.id.mainFragment) { result ->
            requireActivity().showToast(result, requireView())
        }

        binding.mainRecyclerView.adapter = mainAdapter
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getRooms()
        viewModel.result.observe(viewLifecycleOwner) {
            mainAdapter.submitList(it.toMutableList())
        }

        onClickAddImageView()
    }

    private fun startDetailAdminFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_detailAdminFragment)
    }

    private fun startDetailUserFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_detailUserFragment)
    }

    private fun initRoomDialogFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_roomDialogFragment)
    }

    private fun onClickAddImageView() {
        binding.rollingPaperAddImageView.setOnClickListener {
            initRoomDialogFragment()
        }

        binding.settingButton.setOnClickListener {
            requireActivity().showToast("테스트입니다!", it)
        }
    }
}