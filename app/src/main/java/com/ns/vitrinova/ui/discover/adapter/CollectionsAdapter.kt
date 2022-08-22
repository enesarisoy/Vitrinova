package com.ns.vitrinova.ui.discover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.data.model.Collections
import com.ns.vitrinova.databinding.ItemCollectionsBinding
import com.ns.vitrinova.utils.downloadImage

class CollectionsAdapter : RecyclerView.Adapter<CollectionsAdapter.CollectionsViewHolder>() {
    inner class CollectionsViewHolder(private val binding: ItemCollectionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(collections: Collections) {
            binding.run {
                ivCollection.downloadImage(collections.cover.url)
                tvTitle.text = collections.title
                tvDefinition.text = collections.definition
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Collections>() {
        override fun areItemsTheSame(oldItem: Collections, newItem: Collections): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Collections,
            newItem: Collections
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionsAdapter.CollectionsViewHolder =
        CollectionsViewHolder(
            ItemCollectionsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: CollectionsAdapter.CollectionsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}