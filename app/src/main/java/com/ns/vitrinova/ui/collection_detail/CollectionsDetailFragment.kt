package com.ns.vitrinova.ui.collection_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.R
import com.ns.vitrinova.data.model.Collections
import com.ns.vitrinova.databinding.FragmentCollectionsDetailBinding
import com.ns.vitrinova.ui.MainActivity
import com.ns.vitrinova.ui.base.BaseFragment
import com.ns.vitrinova.ui.discover.DiscoverViewModel
import com.ns.vitrinova.ui.products_detail.ProductsDetailAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionsDetailFragment : BaseFragment<FragmentCollectionsDetailBinding>(
    FragmentCollectionsDetailBinding::inflate
) {

    private val viewModel: DiscoverViewModel by viewModels()
    private lateinit var collectionDetailAdapter: CollectionDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backPressed()
        initData()

    }

    private fun backPressed() {
        binding.apply {
            btnBack.setOnClickListener {
                (activity as MainActivity).onBackPressed()
            }
        }
    }

    private fun initData() {
        binding.progressBar.visibility = View.VISIBLE

        viewModel.postContent.observe(requireActivity()) {
            it.let {
                binding.run {
                    textTitle.text = it[3].title

                }
                initCollections(it[3].collections)
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun initCollections(collections: List<Collections>) {
        binding.apply {
            collectionDetailAdapter = CollectionDetailAdapter()
            recyclerViewCollections.adapter = collectionDetailAdapter
            recyclerViewCollections.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        collectionDetailAdapter.differ.submitList(collections)
    }
}