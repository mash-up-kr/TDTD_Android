package com.tdtd.presentation.ui.main

import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
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
        handleDynamicLink()
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
        mainAdapter = MainAdapter({ room ->
            if (room.is_host) startDetailAdminFragment(room.room_code, room.created_at)
            else startDetailUserFragment(room.room_code)

        }, { favorite ->
            if (favorite.is_bookmark) mainViewModel.deleteBookmarkByRoomCode(favorite.room_code)
            else mainViewModel.postBookmarkByRoomCode(favorite.room_code)
        })

        binding.mainRecyclerView.adapter = mainAdapter
    }

    private fun setBookmarkList() {
        binding.rollingPaPerCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) mainViewModel.getUserBookmarkList()
            else mainViewModel.getUserRoomList()
        }
    }

    private fun handleDynamicLink() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(requireActivity().intent)
            .addOnSuccessListener { pendingDynamicLinkData ->
                val deepLink: Uri?
                val roomCode: String
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    roomCode = deepLink.toString().substring(30)

                    mainViewModel.postParticipateByRoomCode(roomCode).apply {
                        startDetailAdminFragment(roomCode, "dynamic_link")
                    }
                }
            }
            .addOnFailureListener {}
    }

    private fun setNavigationResult() {
        getNavigationResult<String>(R.id.mainFragment, "detail") { result ->
            requireActivity().showToast(result, requireView())
        }.let {
            mainViewModel.getUserRoomList()
        }
    }

    private fun startDetailAdminFragment(roomCode: String, date: String) {
        val action = MainFragmentDirections.actionMainFragmentToDetailAdminFragment(roomCode, date)
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
            findNavController().navigate(R.id.roomDialogFragment)
        }

        binding.settingButton.setOnClickListener {
            requireActivity().showToast("테스트입니다!", it)
        }
    }
}