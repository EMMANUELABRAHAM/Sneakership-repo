package com.company.sneakership.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.sneakership.databinding.ItemImageViewBinding

class SneakerImagePagerAdapter(private val imageList: List<String>) :
    RecyclerView.Adapter<SneakerImagePagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemImageViewBinding =
            ItemImageViewBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    inner class ImageViewHolder(private val binding: ItemImageViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Glide.with(binding.root)
                .load(imageUrl)
                .centerCrop()
                .into(binding.imageView)
        }
    }
}
