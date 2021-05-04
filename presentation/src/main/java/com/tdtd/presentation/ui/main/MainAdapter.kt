package com.tdtd.presentation.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdtd.presentation.BR
import com.tdtd.presentation.R
import com.tdtd.presentation.databinding.RowMainUserRoomItemBinding
import com.tdtd.presentation.entity.Room

class MainAdapter(
    private val onClick: (room: Room) -> Unit,
    private val isFavorite : (room:Room) -> Unit
) : ListAdapter<Room, MainAdapter.MainViewHolder>(CategoryDiffCallback()) {

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

        fun bind(data: Room) {
            Log.e("df", data.toString())
            binding.setVariable(BR.room, data)
            binding.container.setOnClickListener {
                onClick(data)
            }
            binding.favoritesButton.isChecked = data.is_bookmark
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class CategoryDiffCallback : DiffUtil.ItemCallback<Room>() {
    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem.created_at == newItem.created_at
    }

    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem == newItem
    }
}
