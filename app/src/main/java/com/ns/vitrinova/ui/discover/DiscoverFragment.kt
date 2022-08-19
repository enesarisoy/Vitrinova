package com.ns.vitrinova.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.vitrinova.data.model.Product
import com.ns.vitrinova.databinding.FragmentDiscoverBinding
import com.ns.vitrinova.ui.base.BaseFragment
import com.ns.vitrinova.ui.discover.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(
    FragmentDiscoverBinding::inflate
) {

    private val TAG = "DiscoverFragment"
    private val viewModel: DiscoverViewModel by activityViewModels()
    private lateinit var productsAdapter: ProductsAdapter
//    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        initProducts()
        initCategories()


    }

    private fun initProducts() {
        viewModel.postContent.observe(viewLifecycleOwner) {
            it?.let {
                binding.run {
                    labelNewProducts.text = it[1].title
                }
                productsAdapter.differ.submitList(it[1].products)
            }
        }
    }

    private fun initCategories() {
        binding.apply {
//            recyclerViewCategories.adapter = categoriesAdapter
//            recyclerViewCategories.layoutManager =
//                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.postContent.observe(viewLifecycleOwner) {
            it?.let {
                binding.run {
                    labelCategories.text = it[2].title
                }

//                categoriesAdapter.addItems(it[2].categories)
//                categoriesAdapter.differ.submitList(it[2].categories)
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            productsAdapter = ProductsAdapter()
            recyclerViewProducts.adapter = productsAdapter
            recyclerViewProducts.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }


}