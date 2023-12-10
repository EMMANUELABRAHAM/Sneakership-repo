package com.company.sneakership.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.sneakership.R
import com.company.sneakership.databinding.ListItemSneakerBinding
import com.company.sneakership.model.Sneaker
import com.company.sneakership.ui.adapter.listners.HomeSneakerListListener

class SneakerAdapter :
    ListAdapter<Sneaker, SneakerAdapter.SneakerViewHolder>(SneakerDiffCallback()) {

    private var itemClickListener: HomeSneakerListListener? = null

    fun setOnItemClickListener(listener: HomeSneakerListListener) {
        this.itemClickListener = listener
    }

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
            binding.sneakerPrice.text = "$${sneaker.retailPrice.toString()}"
            Glide.with(itemView.context).load(sneaker.media?.imageUrl).circleCrop().centerCrop()
                .into(binding.sneakerImage)
            binding.sneakerImage.setOnClickListener {
                sneaker.id?.let { it1 -> itemClickListener?.itemClick(it1) }
            }
            binding.sneakerName.setOnClickListener {
                sneaker.id?.let { it1 -> itemClickListener?.itemClick(it1) }
            }
            binding.sneakerPrice.setOnClickListener {
                sneaker.id?.let { it1 -> itemClickListener?.itemClick(it1) }
            }
            binding.plusIcon.setOnClickListener {
                sneaker.addedToCart.let { addedToCart ->
                    if (!addedToCart) {
                        binding.plusIcon.setImageResource(R.drawable.baseline_cancel_24)
                    } else {
                        binding.plusIcon.setImageResource(R.drawable.round_add_circle_24)
                    }
                    sneaker.id?.let { id ->
                        itemClickListener?.cartIconClick(id)
                    }
                }
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
