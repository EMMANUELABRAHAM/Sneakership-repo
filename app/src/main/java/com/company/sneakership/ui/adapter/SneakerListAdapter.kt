package com.company.sneakership.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.sneakership.databinding.ListItemSneakerBinding
import com.company.sneakership.model.Sneaker

class SneakerAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<Sneaker, SneakerAdapter.SneakerViewHolder>(SneakerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        val binding: ListItemSneakerBinding =
            ListItemSneakerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SneakerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        val sneaker = getItem(position)
        holder.bind(sneaker)
    }

    inner class SneakerViewHolder(private val binding: ListItemSneakerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sneaker: Sneaker) {
            binding.sneakerName.text = sneaker.name
            binding.sneakerPrice.text = sneaker.retailPrice.toString()
            Glide.with(itemView.context)
                .load(sneaker.media?.thumbUrl)
                .circleCrop()
                .centerInside()
                .into(binding.sneakerImage)
            binding.root.setOnClickListener {
                sneaker.id?.let { it1 -> onItemClick(it1) }
            }
        }
    }
}

class SneakerDiffCallback : DiffUtil.ItemCallback<Sneaker>() {
    override fun areItemsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
        return oldItem == newItem
    }
}
