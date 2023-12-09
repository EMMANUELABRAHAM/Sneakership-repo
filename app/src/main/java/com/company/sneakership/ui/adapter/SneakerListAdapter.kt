package com.company.sneakership.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.sneakership.databinding.ListItemSneakerBinding
import com.company.sneakership.model.Sneaker

class SneakerAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<Sneaker, SneakerAdapter.SneakerViewHolder>(DiffCallback) {

    // ViewHolder class
    class SneakerViewHolder(private val binding: ListItemSneakerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sneaker: Sneaker) {
            binding.SneakerName.text = sneaker.name
        }
    }

    // DiffUtil Callback
    companion object DiffCallback : DiffUtil.ItemCallback<Sneaker>() {
        override fun areItemsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem == newItem
        }
    }

    // Create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSneakerBinding.inflate(inflater, parent, false)
        return SneakerViewHolder(binding)
    }

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        val sneaker = getItem(position)
        holder.bind(sneaker)

        // Set click listener
        holder.itemView.setOnClickListener {
            onItemClick.invoke(sneaker.id)
        }
    }
}
