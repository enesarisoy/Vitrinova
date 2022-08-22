package com.ns.vitrinova.ui.discover.adapter

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
import com.ns.vitrinova.databinding.ItemEditorShopsBinding
import com.ns.vitrinova.utils.downloadImage


class ShopsAdapter : RecyclerView.Adapter<ShopsAdapter.ShopsViewHolder>() {
    inner class ShopsViewHolder(private val binding: ItemEditorShopsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shop: Shop) {
            binding.run {
                itemView.animation =
                    AnimationUtils.loadAnimation(itemView.context, R.anim.anim_horizontal_recyclerview)

                shop.cover?.let { imgLogo.downloadImage(it.url) }

                imgShop1.downloadImage(shop.popular_products[0].images[0].url)
                imgShop2.downloadImage(shop.popular_products[1].images[0].url)
                imgShop3.downloadImage(shop.popular_products[2].images[0].url)

                textTitle.text = shop.name
                textShop.text = shop.definition
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopsViewHolder =
        ShopsViewHolder(
            ItemEditorShopsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ShopsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}