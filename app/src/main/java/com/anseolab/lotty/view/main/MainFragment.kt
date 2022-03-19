package com.anseolab.lotty.view.main

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentMainBinding
import com.anseolab.lotty.extensions.hideAll
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.main.around.AroundFragment
import com.anseolab.lotty.view.main.qr.QrFragment
import com.anseolab.lotty.view.main.random.RandomFragment
import com.anseolab.lotty.view.main.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class MainFragment : ViewModelFragment<FragmentMainBinding, MainViewModelType>(
    R.layout.fragment_main
) {

    private var backKeyPressedTime: Long = 0

    private val _viewModel: MainViewModel by viewModels()
    override val viewModel: MainViewModelType get() = _viewModel

    override fun onBackPressed() {
        viewModel.input.onBackPressed()
    }

    override fun onWillAttachViewModel(
        viewDataBinding: FragmentMainBinding,
        viewModel: MainViewModelType
    ) {
        with(viewModel.output) {
            isBackPressDispatcherEnabled = true

            selectedPage.observe {
                when (it) {
                    0 -> changeFragment(AroundFragment.fragmentClass, AroundFragment.name)
                    1 -> changeFragment(SearchFragment.fragmentClass, SearchFragment.name)
                    2 -> changeFragment(RandomFragment.fragmentClass, RandomFragment.name)
                    3 -> changeFragment(QrFragment.fragmentClass, QrFragment.name)
                }
            }

            showBackPress.observe {
                Toast.makeText(requireContext(), "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }

            finish.observe {
                requireActivity().finish()
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