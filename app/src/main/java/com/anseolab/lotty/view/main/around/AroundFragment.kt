package com.anseolab.lotty.view.main.around

import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentAroundBinding
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class AroundFragment: ViewModelFragment<FragmentAroundBinding, AroundViewModelType>(
    R.layout.fragment_around
) {
    private val _viewModel: AroundViewModel by viewModels()
    override val viewModel: AroundViewModelType get() = _viewModel



    companion object: FragmentLauncher<AroundFragment>() {
        override val fragmentClass: KClass<AroundFragment>
            get() = AroundFragment::class
    }
}