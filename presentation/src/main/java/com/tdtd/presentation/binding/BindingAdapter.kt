package com.tdtd.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdtd.presentation.entity.PresenterRoomDetailEntity
import com.tdtd.presentation.entity.Room
import com.tdtd.presentation.ui.detail.DetailAdapter
import com.tdtd.presentation.ui.main.MainAdapter

@BindingAdapter("bindRoomList")
fun bindRecyclerView(recyclerView: RecyclerView, list: List<Room>?) {
    list?.let { roomList ->
        (recyclerView.adapter as MainAdapter).submitList(roomList)
    }
}

@BindingAdapter("bindRoomDetail")
fun bindRecyclerView(recyclerView: RecyclerView, list: PresenterRoomDetailEntity?) {
    list?.let { entity ->
        (recyclerView.adapter as DetailAdapter).submitList(entity.result.comments)
    }
}