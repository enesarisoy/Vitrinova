package com.ns.vitrinova.ui.new_shops

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.R
import com.ns.vitrinova.data.model.Shop
import com.ns.vitrinova.databinding.ItemNewShopsBinding
import com.ns.vitrinova.databinding.ItemShopDetailBinding
import com.ns.vitrinova.utils.downloadImage


class NewShopsAdapter() : RecyclerView.Adapter<NewShopsAdapter.NewShopViewHolder>() {
    inner class NewShopViewHolder(private val binding: ItemNewShopsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shop: Shop) {
            itemView.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.anim_vertical_recyclerview)

            binding.run {
                shop.logo?.let {
                    imgLogo.downloadImage(it.url)
                }
                shop.cover?.let {
                    imgShop.downloadImage(it.url)
                }
                textTitle.text = shop.name
                textDescription.text = shop.definition
                textProductCount.text = "${shop.product_count} ÜRÜN"
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
    ): NewShopViewHolder =
        NewShopViewHolder(
            ItemNewShopsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: NewShopsAdapter.NewShopViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}