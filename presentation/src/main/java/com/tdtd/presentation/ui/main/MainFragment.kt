package com.tdtd.presentation.ui.main

import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.ktx.Firebase
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mainAdapter: MainAdapter
    private var deepLinkFlag = true
    lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun initViews() {
        super.initViews()

        initBindings()
        homeAnalytics()
        setAdapter()
        setBookmarkList()
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
        mainViewModel.getUserRoomList()
    }

    private fun initBindings() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mainViewModel
    }

    private fun homeAnalytics() {
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent("HomeView", null)
    }

    private fun setAdapter() {
        mainAdapter = MainAdapter({ room ->
            if (room.is_host) startDetailAdminFragment(
                room.room_code,
                room.created_at,
                room.is_bookmark
            )
            else startDetailUserFragment(room.room_code, room.is_bookmark)
        }, { addFavorite ->
            mainViewModel.postBookmarkByRoomCode(addFavorite.room_code)
        }, { deleteFavorite ->
            mainViewModel.deleteBookmarkByRoomCode(deleteFavorite.room_code)
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
        if (deepLinkFlag) {
            FirebaseDynamicLinks.getInstance()
                .getDynamicLink(requireActivity().intent)
                .addOnSuccessListener { pendingDynamicLinkData ->
                    val deepLink: Uri?
                    val roomCode: String
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link
                        roomCode = deepLink.toString().substring(30)

                        mainViewModel.postParticipateByRoomCode(roomCode).apply {
                            startDetailFragment(roomCode, getString(R.string.make_new_room))
                            deepLinkFlag = false
                        }
                    }
                }
                .addOnFailureListener {}
        }
    }

    private fun startDetailFragment(roomCode: String, date: String) {
        val action =
            MainFragmentDirections.actionGlobalDetailFragment(
                roomCode, date,
                host = false,
                bookmark = false
            )
        findNavController().navigate(action)
    }

    private fun startDetailAdminFragment(roomCode: String, date: String, bookmark: Boolean) {
        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                roomCode,
                date,
                true,
                bookmark
            )
        findNavController().navigate(action)
    }

    private fun startDetailUserFragment(roomCode: String, bookmark: Boolean) {
        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(roomCode, "", false, bookmark)
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
            findNavController().navigate(R.id.action_mainFragment_to_roomDialogFragment)
        }
    }
}