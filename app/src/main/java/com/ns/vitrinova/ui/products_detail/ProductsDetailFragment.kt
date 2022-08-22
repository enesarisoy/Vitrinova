package com.ns.vitrinova.ui.products_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.R
import com.ns.vitrinova.data.model.Product
import com.ns.vitrinova.databinding.FragmentProductDetailBinding
import com.ns.vitrinova.ui.MainActivity
import com.ns.vitrinova.ui.base.BaseFragment
import com.ns.vitrinova.ui.discover.DiscoverViewModel
import com.ns.vitrinova.ui.discover.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsDetailFragment() : BaseFragment<FragmentProductDetailBinding>(
    FragmentProductDetailBinding::inflate
) {

    private val viewModel: DiscoverViewModel by viewModels()
    private lateinit var productsDetailAdapter: ProductsDetailAdapter

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
                    textTitle.text = it[1].title

                }
                initProducts(it[1].products)
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun initProducts(products: List<Product>) {
        binding.apply {
            productsDetailAdapter = ProductsDetailAdapter()
            recyclerViewProducts.adapter = productsDetailAdapter
            recyclerViewProducts.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        productsDetailAdapter.differ.submitList(products)

    }


}