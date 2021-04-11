package com.tdtd.presentation.ui.detail

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.ActivityDetailUserBinding
import com.tdtd.presentation.entity.RoomContents
import com.tdtd.presentation.entity.getDefaultCharacter
import com.tdtd.presentation.entity.getRoomContents
import com.tdtd.presentation.entity.getSelectedCharacter
import com.tdtd.presentation.ui.dialog.DialogConstructor
import com.tdtd.presentation.ui.writetext.WriteTextDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_detail_room_contents.*
import kotlinx.android.synthetic.main.row_detail_items.view.*

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {

    private lateinit var inflater: LayoutInflater
    private lateinit var view: View

    private lateinit var binding: ActivityDetailUserBinding
    private val contentList: List<RoomContents> = getRoomContents()

    companion object {
        var currentPosition: Int = 0
        var prevPosition: Int = 0
    }

    private var detailAdapter = DetailAdapter() { position ->
        // TODO: companion object를 포함하여 테스트 용으로 추가한 코드라 수정해야 합니다. 스크롤하면서 터치하면 앱 죽어요.....
        prevPosition = currentPosition
        currentPosition = position
        recyclerView.get(prevPosition).characterImageView.setImageResource(
            getDefaultCharacter(
                contentList.get(prevPosition).sticker_color
            )
        )
        recyclerView.get(currentPosition).characterImageView.setImageResource(
            getSelectedCharacter(
                contentList.get(currentPosition).sticker_color
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        initView()
        onClickWriteButton()
        onClickFavoritesButton()
        onClickBackButton()
        onClickLeaveRoomButton()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_user)
        binding.lifecycleOwner = this

        inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // TODO: contents 유무에 따라 view가 달라져야 합니다.
        view = inflater.inflate(
            R.layout.layout_detail_room_contents,
            binding.detailUserFrameLayout,
            false
        )
//        view = inflater.inflate(R.layout.layout_detail_user, binding.detailUserFrameLayout, false)
        binding.detailUserFrameLayout.addView(view)

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        detailAdapter.submitList(contentList)
        recyclerView.adapter = detailAdapter
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun onClickFavoritesButton() {
        binding.favoritesButton.setOnClickListener {
            binding.favoritesButton.isSelected = !binding.favoritesButton.isSelected
        }
    }

    private fun onClickWriteButton() {
        binding.writeButton.setOnClickListener {
            initVoiceRecordDialogFragment()
        }
    }

    private fun initVoiceRecordDialogFragment() {
        val bottomSheet = WriteTextDialogFragment()
        bottomSheet.run {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
            show(supportFragmentManager, bottomSheet.tag)
        }
    }

    private fun onClickBackButton() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun onClickLeaveRoomButton() {
        binding.leaveRoomButton.setOnClickListener {
            val dialog = DialogConstructor.getInstance(submitButtonClicked = {
                intent.putExtra("isLeaveRoom", true)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }, R.layout.dialog_leave_room)
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }
}