package com.ns.vitrinova.ui.shops_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ns.vitrinova.R
import com.ns.vitrinova.databinding.FragmentShopsDetailBinding
import com.ns.vitrinova.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopsDetailFragment: BaseFragment<FragmentShopsDetailBinding>(
    FragmentShopsDetailBinding::inflate
) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}