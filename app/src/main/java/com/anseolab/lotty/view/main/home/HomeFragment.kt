package com.anseolab.lotty.view.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentHomeBinding
import com.anseolab.lotty.view.base.BaseFragment
import com.anseolab.lotty.view.base.FragmentLauncher
import kotlin.reflect.KClass

class HomeFragment: BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    override val showBackButton: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewDataBinding) {

        }
    }


    companion object: FragmentLauncher<HomeFragment>() {
        override val fragmentClass: KClass<HomeFragment>
            get() = HomeFragment::class
    }
}