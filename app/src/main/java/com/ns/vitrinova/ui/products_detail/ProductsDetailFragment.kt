package com.ns.vitrinova.ui.products_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.data.model.Product
import com.ns.vitrinova.databinding.FragmentProductDetailBinding
import com.ns.vitrinova.ui.MainActivity
import com.ns.vitrinova.ui.base.BaseFragment
import com.ns.vitrinova.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsDetailFragment : BaseFragment<FragmentProductDetailBinding>(
    FragmentProductDetailBinding::inflate
) {

    private val viewModel: MainViewModel by viewModels()
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
                    tvTitle.text = it[1].title

                }
                initProducts(it[1].products)
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun initProducts(products: List<Product>) {
        binding.apply {
            productsDetailAdapter = ProductsDetailAdapter()
            rvProducts.adapter = productsDetailAdapter
            rvProducts.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        productsDetailAdapter.differ.submitList(products)

    }


}