package com.anseolab.lotty.view.main.search

import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentSearchBinding
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class SearchFragment: ViewModelFragment<FragmentSearchBinding, SearchViewModelType>(
    R.layout.fragment_search
) {
    private val _viewModel: SearchViewModel by viewModels()
    override val viewModel: SearchViewModelType get() = _viewModel

    companion object: FragmentLauncher<SearchFragment>() {
        override val fragmentClass: KClass<SearchFragment>
            get() = SearchFragment::class
    }
}