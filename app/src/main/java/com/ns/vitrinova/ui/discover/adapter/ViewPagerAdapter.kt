package com.ns.vitrinova.ui.discover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.data.model.Featured
import com.ns.vitrinova.databinding.ItemFeaturedBinding
import com.ns.vitrinova.utils.downloadImage

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(private val binding: ItemFeaturedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(featured: Featured) {
            binding.run {
                ivFeatured.downloadImage(featured.cover.thumbnail.url)
                tvTitle.text = featured.title
                tvSubtitle.text = featured.sub_title
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Featured>() {
        override fun areItemsTheSame(oldItem: Featured, newItem: Featured): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Featured,
            newItem: Featured
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        ViewPagerViewHolder(
            ItemFeaturedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}