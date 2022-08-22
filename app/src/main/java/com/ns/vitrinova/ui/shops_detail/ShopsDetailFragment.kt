package com.ns.vitrinova.ui.shops_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.R
import com.ns.vitrinova.data.model.Shop
import com.ns.vitrinova.databinding.FragmentShopsDetailBinding
import com.ns.vitrinova.ui.MainActivity
import com.ns.vitrinova.ui.base.BaseFragment
import com.ns.vitrinova.ui.discover.DiscoverViewModel
import com.ns.vitrinova.ui.products_detail.ProductsDetailAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopsDetailFragment : BaseFragment<FragmentShopsDetailBinding>(
    FragmentShopsDetailBinding::inflate
) {

    private val viewModel: DiscoverViewModel by viewModels()
    private lateinit var shopsDetailAdapter: ShopsDetailAdapter

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
                    textTitle.text = it[4].title

                }
                initShops(it[4].shops)
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun initShops(shops: List<Shop>) {
        binding.apply {
            shopsDetailAdapter = ShopsDetailAdapter()
            recyclerViewShops.adapter = shopsDetailAdapter
            recyclerViewShops.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        shopsDetailAdapter.differ.submitList(shops)
    }
}