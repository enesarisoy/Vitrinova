package com.ns.vitrinova.ui.discover

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.material.tabs.TabLayoutMediator
import com.ns.vitrinova.R
import com.ns.vitrinova.data.model.*
import com.ns.vitrinova.data.model.Collections
import com.ns.vitrinova.databinding.FragmentDiscoverBinding
import com.ns.vitrinova.ui.MainViewModel
import com.ns.vitrinova.ui.base.BaseFragment
import com.ns.vitrinova.ui.custom.FeaturedViewPagerTransformer
import com.ns.vitrinova.ui.discover.adapter.*
import com.ns.vitrinova.utils.Constants.REQUEST_CODE_STT
import com.ns.vitrinova.utils.Utils
import com.ns.vitrinova.utils.downloadImage
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(
    FragmentDiscoverBinding::inflate
) {

    private val viewModel: MainViewModel by activityViewModels()
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
        initSpeech()
        getData()
        listenDiscoverData()

    }

    private fun initSpeech() {
        binding.btnVoice.setOnClickListener {
            askSpeechInput()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_STT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if (!result.isNullOrEmpty()) {
                        val recognizedText = result[0]
                        binding.etSearch.setText(recognizedText)
                    }
                }
            }
        }
    }

   /* var response = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == REQUEST_CODE_STT) {
            val resulte = result.data?.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!resulte.isNullOrEmpty()) {
                val recognizedText = resulte[0]
                binding.etSearch.setText(recognizedText)
            }
        }
    }*/

    private fun askSpeechInput() {
        val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")
        try {
            startActivityForResult(sttIntent, REQUEST_CODE_STT)
//            response.launch(sttIntent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                requireView().context,
                "Error!!",
                Toast.LENGTH_LONG
            ).show()
        }
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
            rvProducts.adapter = productsAdapter
            rvProducts.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        productsAdapter.differ.submitList(products)

    }

    private fun initCategories(categories: List<Category>) {
        binding.run {
            categoriesAdapter = CategoriesAdapter()
            rvCategories.adapter = categoriesAdapter
            rvCategories.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        categoriesAdapter.differ.submitList(categories)
    }

    private fun initCollections(collections: List<Collections>) {
        binding.run {
            collectionsAdapter = CollectionsAdapter()
            rvCollections.adapter = collectionsAdapter
            rvCollections.layoutManager =
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
            rvEditorShops.adapter = shopsAdapter
            rvEditorShops.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        shopsAdapter.differ.submitList(shops)

        val snapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.attachToRecyclerView(binding.rvEditorShops)
        shops[0].cover?.let { cover ->
            binding.ivEditorBackground.downloadImage(cover.url)
        }

        binding.rvEditorShops.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollIsIdle = newState != RecyclerView.SCROLL_STATE_DRAGGING
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (scrollIsIdle) {
                    binding.rvEditorShops.layoutManager?.let { manager ->
                        val snapView = snapHelper.findSnapView(manager)
                        val snapPosition = snapView?.let { snap -> manager.getPosition(snap) }
                        snapPosition?.let { position ->
                            shops[position].cover?.let { cover ->
                                binding.ivEditorBackground.downloadImage(cover.url)
                            }
                        }
                    }
                }
            }
        })
    }


}