package com.ns.vitrinova.ui.discover

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.material.tabs.TabLayoutMediator
import com.ns.vitrinova.R
import com.ns.vitrinova.data.model.*
import com.ns.vitrinova.databinding.FragmentDiscoverBinding
import com.ns.vitrinova.ui.base.BaseFragment
import com.ns.vitrinova.ui.custom.FeaturedViewPagerTransformer
import com.ns.vitrinova.ui.discover.adapter.*
import com.ns.vitrinova.utils.Utils
import com.ns.vitrinova.utils.downloadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(
    FragmentDiscoverBinding::inflate
) {

    private val TAG = "DiscoverFragment"
    private val viewModel: DiscoverViewModel by activityViewModels()
    private var scrollIsIdle: Boolean = false
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var collectionsAdapter: CollectionsAdapter
    private lateinit var shopsAdapter: ShopsAdapter
    private lateinit var newShopsAdapter: NewShopsAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
        getData()
        listenDiscoverData()
    }

    private fun initClick() {
        binding.run {
            btnAllProducts.setOnClickListener {
                findNavController().navigate(R.id.action_discoverFragment_to_productsDetailFragment)
            }
            btnAllCollections.setOnClickListener {
                findNavController().navigate(R.id.action_discoverFragment_to_collectionsDetailFragment)
            }
            btnAllNewShops.setOnClickListener {
                findNavController().navigate(R.id.action_discoverFragment_to_newShopsFragment)
            }
            btnEditorShops.setOnClickListener {
                findNavController().navigate(R.id.action_discoverFragment_to_shopsDetailFragment)
            }

            swipeRefreshLayout.setOnRefreshListener {
                getData()
            }
        }
    }

    private fun getData() {
        if (Utils.isNetworkAvailable(requireContext())) {
            binding.run {
                swipeRefreshLayout.isRefreshing = true
                emptyLoadingView.visibility = View.VISIBLE
                viewModel.getContent()
            }
        } else
            Toast.makeText(activity, R.string.no_internet, Toast.LENGTH_SHORT).show()
    }

    private fun listenDiscoverData() {
        viewModel.postContent.observe(viewLifecycleOwner) {
            it?.let {
                binding.run {
                    labelNewProducts.text = it[1].title
                    labelCategories.text = it[2].title
                    labelCollections.text = it[3].title
                    labelEditorShops.text = it[4].title
                    labelNewShops.text = it[5].title

                    swipeRefreshLayout.isRefreshing = false
                    emptyLoadingView.visibility = View.GONE
                }
                initViewPager(it[0].featured)
                initProducts(it[1].products)
                initCategories(it[2].categories)
                initCollections(it[3].collections)
                initEditorShops(it[4].shops)
                initNewShops(it[5].shops)
            }
        }

    }

    private fun initProducts(products: List<Product>) {
        binding.apply {
            productsAdapter = ProductsAdapter()
            recyclerViewProducts.adapter = productsAdapter
            recyclerViewProducts.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        productsAdapter.differ.submitList(products)

    }

    private fun initCategories(categories: List<Category>) {
        binding.run {
            categoriesAdapter = CategoriesAdapter()
            recyclerViewCategories.adapter = categoriesAdapter
            recyclerViewCategories.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        categoriesAdapter.differ.submitList(categories)
    }

    private fun initCollections(collections: List<Collections>) {
        binding.run {
            collectionsAdapter = CollectionsAdapter()
            recyclerViewCollections.adapter = collectionsAdapter
            recyclerViewCollections.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        collectionsAdapter.differ.submitList(collections)
    }

    private fun initNewShops(shops: List<Shop>) {
        binding.run {
            newShopsAdapter = NewShopsAdapter()
            recyclerViewNewShops.adapter = newShopsAdapter
            recyclerViewNewShops.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        newShopsAdapter.differ.submitList(shops)
    }

    private fun initViewPager(featured: List<Featured>) {
        binding.run {
            viewPagerAdapter = ViewPagerAdapter()
            featuresViewPager.setPageTransformer(FeaturedViewPagerTransformer())
            featuresViewPager.adapter = viewPagerAdapter

            TabLayoutMediator(tabDots, featuresViewPager) { _, _ -> }.attach()
        }
        viewPagerAdapter.differ.submitList(featured)
    }

    private fun initEditorShops(shops: List<Shop>) {
        binding.run {
            shopsAdapter = ShopsAdapter()
            recyclerViewEditorShops.adapter = shopsAdapter
            recyclerViewEditorShops.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        shopsAdapter.differ.submitList(shops)

        val snapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(binding.recyclerViewEditorShops)
        shops[0].cover?.let { cover ->
            binding.imgEditorBackground.downloadImage(cover.url)
        }

        binding.recyclerViewEditorShops.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollIsIdle = newState != RecyclerView.SCROLL_STATE_DRAGGING
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (scrollIsIdle) {
                    binding.recyclerViewEditorShops.layoutManager?.let { manager ->
                        val snapView = snapHelper.findSnapView(manager)
                        val snapPosition = snapView?.let { snap -> manager.getPosition(snap) }
                        snapPosition?.let { position ->
                            shops[position].cover?.let { cover ->
                                binding.imgEditorBackground.downloadImage(cover.url)
                            }
                        }
                    }
                }
            }
        })
    }


}