package com.ns.vitrinova.ui.collection_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.R
import com.ns.vitrinova.data.model.Collections
import com.ns.vitrinova.databinding.ItemCollectionDetailBinding
import com.ns.vitrinova.utils.downloadImage


class CollectionDetailAdapter() :
    RecyclerView.Adapter<CollectionDetailAdapter.CollectionViewHolder>() {
    inner class CollectionViewHolder(private val binding: ItemCollectionDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(collections: Collections) {
            itemView.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.anim_vertical_recyclerview)

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
    ): CollectionViewHolder =
        CollectionViewHolder(
            ItemCollectionDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(
        holder: CollectionDetailAdapter.CollectionViewHolder,
        position: Int
    ) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}