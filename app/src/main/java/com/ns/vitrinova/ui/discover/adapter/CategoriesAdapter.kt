package com.ns.vitrinova.ui.discover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.data.model.Category
import com.ns.vitrinova.data.model.Product
import com.ns.vitrinova.databinding.ItemCategoriesBinding
import com.ns.vitrinova.databinding.ItemProductsBinding
import com.ns.vitrinova.utils.downloadImage

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    inner class CategoriesViewHolder(private val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.run {
                imgCategory.downloadImage(category.cover.url)
                textCategory.text = category.name
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.CategoriesViewHolder =
        CategoriesViewHolder(
            ItemCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: CategoriesAdapter.CategoriesViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}