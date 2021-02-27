package com.tdtd.presentation.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdtd.presentation.databinding.RowMainUserRoomItemBinding
import com.tdtd.presentation.entity.Dummy
import com.tdtd.presentation.BR

class MainAdapter() : ListAdapter<Dummy, MainAdapter.MainViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowMainUserRoomItemBinding.inflate(layoutInflater, parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MainViewHolder constructor(val binding: RowMainUserRoomItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Dummy) {
            Log.e("df", data.toString())
            binding.setVariable(BR.dummy, data)
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class CategoryDiffCallback : DiffUtil.ItemCallback<Dummy>() {
    override fun areItemsTheSame(oldItem: Dummy, newItem: Dummy): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Dummy, newItem: Dummy): Boolean {
        return oldItem == newItem
    }
}
