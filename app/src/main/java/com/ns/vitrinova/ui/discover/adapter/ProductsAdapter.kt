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
import com.ns.vitrinova.data.model.Product
import com.ns.vitrinova.databinding.ItemProductsBinding
import com.ns.vitrinova.utils.downloadImage

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
    inner class ProductsViewHolder(private val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            itemView.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.anim_horizontal_recyclerview)
            binding.run {
                tvTitle.text = product.title
                ivProduct.downloadImage(product.images[0].url)
                tvShop.text = product.shop.name
                tvPrice.text = product.price.toString()
                product.old_price?.let {
                    tvOldPrice.run {
                        visibility = View.VISIBLE
                        text = itemView.context.getString(R.string.old_price, product.old_price)
                        paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
            }
        }


    }

    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ItemProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}