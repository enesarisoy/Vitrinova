package com.ns.vitrinova.ui.new_shops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.R
import com.ns.vitrinova.data.model.Product
import com.ns.vitrinova.data.model.Shop
import com.ns.vitrinova.databinding.FragmentNewShopsBinding
import com.ns.vitrinova.databinding.ItemNewShopsBinding
import com.ns.vitrinova.ui.MainActivity
import com.ns.vitrinova.ui.base.BaseFragment
import com.ns.vitrinova.ui.discover.DiscoverViewModel
import com.ns.vitrinova.ui.products_detail.ProductsDetailAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewShopsFragment : BaseFragment<FragmentNewShopsBinding>(
    FragmentNewShopsBinding::inflate
) {

    private val viewModel: DiscoverViewModel by viewModels()
    private lateinit var newShopsAdapter: NewShopsAdapter

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
                    textTitle.text = it[5].title

                }
                initShops(it[5].shops)
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun initShops(shop: List<Shop>) {
        binding.apply {
            newShopsAdapter = NewShopsAdapter()
            recyclerViewShops.adapter = newShopsAdapter
            recyclerViewShops.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        newShopsAdapter.differ.submitList(shop)

    }
}