package com.tdtd.presentation.ui.detail

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tdtd.domain.entity.MakeRoomType
import com.tdtd.presentation.R
import com.tdtd.presentation.base.ui.BaseFragment
import com.tdtd.presentation.databinding.FragmentDetailAdminBinding
import com.tdtd.presentation.ui.main.MainViewModel
import com.tdtd.presentation.ui.reply.RecordVoiceDialogFragment
import com.tdtd.presentation.ui.reply.WriteTextDialogFragment
import com.tdtd.presentation.util.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAdminFragment :
    BaseFragment<FragmentDetailAdminBinding>(R.layout.fragment_detail_admin) {

    private val detailViewModel: DetailViewModel by viewModels()
    private val safeArgs: DetailAdminFragmentArgs by navArgs()
    private lateinit var detailAdapter: DetailAdapter
    private var type: String = ""
    private val mainViewModel: MainViewModel by viewModels()
    private var isFavorite: Boolean = false
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun initViews() {
        super.initViews()

        initBindings()
        setAdapter()
        onClickFavoritesButton()
        onClickBackButton()
        onClickSharedButton()
        onClickMoreButton()
    }

    override fun initObserves() {
        super.initObserves()

        detailViewModel.detailRoom.observe(viewLifecycleOwner, Observer { detailRoom ->
            binding.titleTextView.text = detailRoom.result.title

            type = when (detailRoom.result.type) {
                MakeRoomType.TEXT -> {
                    startWriteTextDetailFragment()
                    "text"
                }
                MakeRoomType.VOICE -> {
                    startRecordVoiceDialogFragment()
                    "voice"
                }
            }
        })

        detailViewModel.getRoomDetailByRoomCode(safeArgs.roomCode)
    }

    private fun initBindings() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = detailViewModel
    }

    private fun setAdapter() {
        detailAdapter = DetailAdapter { comments ->

        }
        binding.detailRecyclerView.adapter = detailAdapter
    }

    private fun startRecordVoiceDialogFragment() {
        binding.writeButton.setOnClickListener {
            val bottomSheet = RecordVoiceDialogFragment()
            bottomSheet.arguments = bundleOf("roomCode" to safeArgs.roomCode)
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun startWriteTextDetailFragment() {
        binding.writeButton.setOnClickListener {
            val bottomSheet = WriteTextDialogFragment()
            bottomSheet.arguments = bundleOf("roomCode" to safeArgs.roomCode)
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun onClickMoreButton() {
        binding.moreButton.setOnClickListener {
            val dialog = DetailSharedBottomSheetFragment()
            dialog.arguments =
                bundleOf("roomCode" to safeArgs.roomCode, "date" to safeArgs.createdAt)
            dialog.show(childFragmentManager, dialog.tag)
        }
    }

    private fun onClickSharedButton() {
        binding.sharedLinkButton.setOnClickListener {
            val dialog = DetailSharedBottomSheetFragment()
            dialog.arguments =
                bundleOf("roomCode" to safeArgs.roomCode, "date" to safeArgs.createdAt)
            dialog.show(childFragmentManager, dialog.tag)
        }
    }

    private fun onClickFavoritesButton() {
        binding.favoritesButton.isSelected = PreferenceManager(requireContext()).getFavorite()

        binding.favoritesButton.setOnClickListener {
            when (isFavorite) {
                true -> {
                    binding.favoritesButton.isSelected = false
                    isFavorite = false
                    PreferenceManager(requireContext()).setFavorite(false)
                    mainViewModel.deleteBookmarkByRoomCode(safeArgs.roomCode)
                }
                false -> {
                    binding.favoritesButton.isSelected = true
                    isFavorite = true
                    PreferenceManager(requireContext()).setFavorite(true)
                    mainViewModel.postBookmarkByRoomCode(safeArgs.roomCode)
                }
            }
        }
    }

    private fun onClickBackButton() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}