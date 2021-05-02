package com.tdtd.presentation.ui.main

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentMainBinding
import com.tdtd.presentation.ui.makeroom.RoomDialogFragment
import com.tdtd.presentation.util.getNavigationResult
import com.tdtd.presentation.util.getNavigationResultLiveData
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
        mainAdapter = MainAdapter { room ->
            if (room.is_host) {
                startDetailAdminFragment(room.room_code)
            } else {
                startDetailUserFragment(room.room_code)
            }
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
        getNavigationResultLiveData<String>("create_room")?.observe(viewLifecycleOwner, Observer {
            mainViewModel.getUserRoomList()
        })

        getNavigationResult<String>(R.id.mainFragment, "detail") { result ->
            requireActivity().showToast(result, requireView())
        }.let {
           mainViewModel.getUserRoomList()
        }
    }

    private fun startDetailAdminFragment(roomCode: String) {
        val action = MainFragmentDirections.actionMainFragmentToDetailAdminFragment(roomCode)
        findNavController().navigate(action)
    }

    private fun startDetailUserFragment(roomCode: String) {
        val action = MainFragmentDirections.actionMainFragmentToDetailUserFragment(roomCode)
        findNavController().navigate(action)
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
            RoomDialogFragment().show(childFragmentManager, RoomDialogFragment().tag)
        }

        binding.settingButton.setOnClickListener {
            requireActivity().showToast("테스트입니다!", it)
        }
    }
}