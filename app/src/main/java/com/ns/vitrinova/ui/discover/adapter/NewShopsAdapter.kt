package com.ns.vitrinova.ui.discover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.R
import com.ns.vitrinova.data.model.Shop
import com.ns.vitrinova.databinding.ItemNewShopsBinding
import com.ns.vitrinova.utils.downloadImage

class NewShopsAdapter() : RecyclerView.Adapter<NewShopsAdapter.NewShopsViewHolder>() {
    inner class NewShopsViewHolder(private val binding: ItemNewShopsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shop: Shop) {
            itemView.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.anim_horizontal_recyclerview)
            binding.run {
                shop.cover?.let { imgShop.downloadImage(it.url) }
                textTitle.text = shop.name
                textDescription.text = shop.definition
                textProductCount.text = "${shop.product_count.toString()} ÜRÜN"
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Shop>() {
        override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Shop,
            newItem: Shop
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewShopsAdapter.NewShopsViewHolder =
        NewShopsViewHolder(
            ItemNewShopsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: NewShopsAdapter.NewShopsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}