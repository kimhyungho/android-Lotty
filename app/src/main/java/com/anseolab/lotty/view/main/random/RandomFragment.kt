package com.anseolab.lotty.view.main.random

import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentAroundBinding
import com.anseolab.lotty.databinding.FragmentRandomBinding
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.main.around.AroundViewModel
import com.anseolab.lotty.view.main.around.AroundViewModelType
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class RandomFragment: ViewModelFragment<FragmentRandomBinding, RandomViewModelType>(
    R.layout.fragment_around
) {
    private val _viewModel: RandomViewModel by viewModels()
    override val viewModel: RandomViewModelType get() = _viewModel


    companion object: FragmentLauncher<RandomFragment>() {
        override val fragmentClass: KClass<RandomFragment>
            get() = RandomFragment::class
    }
}