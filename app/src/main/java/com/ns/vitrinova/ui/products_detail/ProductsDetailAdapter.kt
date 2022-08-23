package com.ns.vitrinova.ui.products_detail

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
import com.ns.vitrinova.databinding.ItemProductDetailBinding
import com.ns.vitrinova.utils.downloadImage


class ProductsDetailAdapter : RecyclerView.Adapter<ProductsDetailAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(private val binding: ItemProductDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            itemView.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.anim_vertical_recyclerview)

            binding.run {
                ivProduct.downloadImage(product.images[0].url)
                if (product.cargo_time == 1)
                    cardFastShipment.visibility = View.VISIBLE

                tvTitle.text = product.title
                tvShop.text = product.shop.name
                tvPrice.text = itemView.context.getString(R.string.price, product.price)
                product.old_price?.let {
                    tvOldPrice.visibility = View.VISIBLE
                    tvOldPrice.text = itemView.context.getString(R.string.old_price, product.old_price)
                    tvOldPrice.paintFlags = tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder =
        ProductViewHolder(
            ItemProductDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ProductsDetailAdapter.ProductViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}