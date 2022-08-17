package com.ns.vitrinova.ui.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ns.vitrinova.R
import com.ns.vitrinova.databinding.FragmentDiscoverBinding
import com.ns.vitrinova.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment: BaseFragment<FragmentDiscoverBinding>(
    FragmentDiscoverBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}