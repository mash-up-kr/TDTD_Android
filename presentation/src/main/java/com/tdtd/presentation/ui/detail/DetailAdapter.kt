package com.tdtd.presentation.ui.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdtd.presentation.BR
import com.tdtd.presentation.databinding.RowDetailItemsBinding
import com.tdtd.presentation.entity.RoomContents
import com.tdtd.presentation.entity.getDefaultCharacter

class DetailAdapter(
    private val onClick: (position: Int) -> Unit
) : ListAdapter<RoomContents, DetailAdapter.DetailViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowDetailItemsBinding.inflate(layoutInflater, parent, false)

        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class DetailViewHolder constructor(val binding: RowDetailItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: RoomContents) {
            Log.e("df", data.toString())
            binding.setVariable(BR.listData, data)
            binding.characterImageView.setImageResource(getDefaultCharacter(data.sticker_color))
            binding.characterImageView.rotation = data.sticker_angle.toFloat()
            binding.characterImageView.setOnClickListener {
                onClick(adapterPosition)
            }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<RoomContents>() {
        override fun areItemsTheSame(oldItem: RoomContents, newItem: RoomContents): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RoomContents, newItem: RoomContents): Boolean {
            return oldItem == newItem
        }
    }

}