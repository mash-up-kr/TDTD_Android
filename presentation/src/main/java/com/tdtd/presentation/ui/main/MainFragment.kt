package com.tdtd.presentation.ui.main

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentMainBinding
import com.tdtd.presentation.util.getNavigationResult
import com.tdtd.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mainAdapter: MainAdapter

    override fun initViews() {
        super.initViews()

        initBindings()
        setAdapter()
        setBookmarkList()
        setNavigationResult()
        onClickAddImageView()
    }

    override fun initObserves() {
        super.initObserves()

        mainViewModel.emptyRoom.observe(viewLifecycleOwner, Observer { roomList ->
            if (roomList) {
                showNoRoomText()
                hideRecyclerView()
            } else {
                hideNoRoomText()
                showRecyclerView()
            }
        })
    }

    private fun initBindings() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mainViewModel
    }

    private fun setAdapter() {
        mainAdapter = MainAdapter {
            // TODO: /api/v1/rooms/{roomCode} 방의 상세 정보
        }

        binding.mainRecyclerView.adapter = mainAdapter
    }

    private fun setBookmarkList() {
        binding.rollingPaPerCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                mainViewModel.getUserBookmarkList()
            else
                mainViewModel.getUserRoomList()
        }
    }

    private fun setNavigationResult() {
        getNavigationResult<String>(R.id.mainFragment) { result ->
            requireActivity().showToast(result, requireView())
        }
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

    private fun showRecyclerView() {
        binding.mainRecyclerView.isVisible = true
    }

    private fun hideRecyclerView() {
        binding.mainRecyclerView.isVisible = false
    }

    private fun showNoRoomText() {
        binding.noRoomTextView.isVisible = true
    }

    private fun hideNoRoomText() {
        binding.noRoomTextView.isVisible = false
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