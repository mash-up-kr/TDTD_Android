package com.tdtd.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdtd.presentation.entity.Room
import com.tdtd.presentation.ui.main.MainAdapter

@BindingAdapter("bindItem")
fun bindRecyclerView(recyclerView: RecyclerView, list: List<Room>?) {
    list?.let {
        (recyclerView.adapter as MainAdapter).submitList(it)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}