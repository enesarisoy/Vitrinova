package com.ns.vitrinova.ui.products_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ns.vitrinova.R
import com.ns.vitrinova.databinding.FragmentProductDetailBinding
import com.ns.vitrinova.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsDetailFragment: BaseFragment<FragmentProductDetailBinding>(
    FragmentProductDetailBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}