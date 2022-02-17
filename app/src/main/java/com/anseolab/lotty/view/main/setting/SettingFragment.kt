package com.anseolab.lotty.view.main.setting

import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentSearchBinding
import com.anseolab.lotty.databinding.FragmentSettingBinding
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.main.search.SearchViewModel
import com.anseolab.lotty.view.main.search.SearchViewModelType
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class SettingFragment: ViewModelFragment<FragmentSettingBinding, SettingViewModelType>(
    R.layout.fragment_setting
) {
    private val _viewModel: SettingViewModel by viewModels()
    override val viewModel: SettingViewModelType get() = _viewModel

    companion object: FragmentLauncher<SettingFragment>() {
        override val fragmentClass: KClass<SettingFragment>
            get() = SettingFragment::class
    }
}