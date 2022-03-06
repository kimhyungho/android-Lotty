package com.anseolab.lotty.view.main

import android.graphics.Color
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentMainBinding
import com.anseolab.lotty.extensions.hideAll
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.main.around.AroundFragment
import com.anseolab.lotty.view.main.home.HomeFragment
import com.anseolab.lotty.view.main.random.RandomFragment
import com.anseolab.lotty.view.main.search.SearchFragment
import com.anseolab.lotty.view.main.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class MainFragment : ViewModelFragment<FragmentMainBinding, MainViewModelType>(
    R.layout.fragment_main
) {

    private val _viewModel: MainViewModel by viewModels()
    override val viewModel: MainViewModelType get() = _viewModel

    override fun onWillAttachViewModel(
        viewDataBinding: FragmentMainBinding,
        viewModel: MainViewModelType
    ) {
        with(viewModel.output) {
            selectedPage.observe {
                when (it) {
                    0 -> changeFragment(HomeFragment::class, HomeFragment.name)
                    1 -> changeFragment(AroundFragment::class, AroundFragment.name)
                    2 -> changeFragment(SearchFragment::class, SearchFragment.name)
                    3 -> changeFragment(RandomFragment::class, RandomFragment.name)
                }
            }
        }
    }

    private fun <T : Fragment> changeFragment(fragmentClass: KClass<T>, className: String) {
        childFragmentManager.beginTransaction()
            .hideAll(childFragmentManager)
            .apply {
                val fragment = childFragmentManager.findFragmentByTag(className)
                if (fragment != null) {
                    show(fragment)
                } else {
                    add(R.id.view_container, fragmentClass.java, null, className)
                }
            }.commit()
    }

}