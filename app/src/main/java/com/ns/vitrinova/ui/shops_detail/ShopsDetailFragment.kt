package com.ns.vitrinova.ui.shops_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ns.vitrinova.data.model.Shop
import com.ns.vitrinova.databinding.FragmentShopsDetailBinding
import com.ns.vitrinova.ui.MainActivity
import com.ns.vitrinova.ui.base.BaseFragment
import com.ns.vitrinova.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopsDetailFragment : BaseFragment<FragmentShopsDetailBinding>(
    FragmentShopsDetailBinding::inflate
) {

    private val viewModel: MainViewModel by viewModels()
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
                    tvTitle.text = it[4].title

                }
                initShops(it[4].shops)
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun initShops(shops: List<Shop>) {
        binding.apply {
            shopsDetailAdapter = ShopsDetailAdapter()
            rvShops.adapter = shopsDetailAdapter
            rvShops.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        shopsDetailAdapter.differ.submitList(shops)
    }
}