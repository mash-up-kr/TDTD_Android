package com.tdtd.presentation.ui.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdtd.presentation.BR
import com.tdtd.presentation.databinding.RowDetailItemsBinding
import com.tdtd.presentation.entity.Comments
import com.tdtd.presentation.entity.getDefaultCharacter
import com.tdtd.presentation.entity.getSelectedCharacter

class DetailAdapter(
    private val onClick: (comments: Comments) -> Unit
) : ListAdapter<Comments, DetailAdapter.DetailViewHolder>(CategoryDiffCallback()) {

    private var selectedPosition = -1

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

        fun bind(data: Comments) {
            binding.setVariable(BR.listData, data)
            binding.characterImageView.setImageResource(getDefaultCharacter(data.presenterSticker_color))
            binding.characterImageView.rotation = data.sticker_angle.toFloat()
            binding.characterImageView.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
                onClick(data)
            }

            if (selectedPosition == adapterPosition) binding.characterImageView.setImageResource(
                getSelectedCharacter(data.presenterSticker_color)
            )
            else binding.characterImageView.setImageResource(getDefaultCharacter(data.presenterSticker_color))
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<Comments>() {
        override fun areItemsTheSame(oldItem: Comments, newItem: Comments): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comments, newItem: Comments): Boolean {
            return oldItem == newItem
        }
    }
}