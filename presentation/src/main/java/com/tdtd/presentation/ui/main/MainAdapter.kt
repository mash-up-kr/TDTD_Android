package com.tdtd.presentation.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdtd.domain.entity.RoomEntity
import com.tdtd.domain.entity.RoomsEntity
import com.tdtd.presentation.BR
import com.tdtd.presentation.databinding.RowMainUserRoomItemBinding
import com.tdtd.presentation.entity.Room

class MainAdapter(
        private val onClick: (position: Int) -> Unit
) : ListAdapter<RoomEntity, MainAdapter.MainViewHolder>(CategoryDiffCallback()) {

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

        fun bind(data: RoomEntity) {
            Log.e("df", data.toString())
            binding.setVariable(BR.room, data)
            binding.container.setOnClickListener {
                onClick(adapterPosition)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class CategoryDiffCallback : DiffUtil.ItemCallback<RoomEntity>() {
    override fun areItemsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean {
        return oldItem.roomCode == newItem.roomCode
    }

    override fun areContentsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean {
        return oldItem == newItem
    }
}
