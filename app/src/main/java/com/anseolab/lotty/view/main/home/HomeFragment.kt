package com.anseolab.lotty.view.main.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentHomeBinding
import com.anseolab.lotty.view.adapter.SlotMachinePagerAdapter
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.databinding.setItem
import com.anseolab.lotty.view.widget.StretchTopNestedScrollView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class HomeFragment : ViewModelFragment<FragmentHomeBinding, HomeViewModelType>(
    R.layout.fragment_home
) {
    override val showBackButton: Boolean = false
    private val slotMachinePagerAdapter = SlotMachinePagerAdapter()
    private val onActionUpListener by lazy {
        object : StretchTopNestedScrollView.OnActionUpListener {
            override fun onActionUpEvent() {
                val nextItem = viewDataBinding.np.currentItem + 5

                viewModel.input.onPageStateChange(nextItem)
                viewDataBinding.np.setItem(nextItem, 1200)
            }
        }
    }

    private val _viewModel: HomeViewModel by viewModels()
    override val viewModel: HomeViewModelType get() = _viewModel

    override fun onWillAttachViewModel(
        viewDataBinding: FragmentHomeBinding,
        viewModel: HomeViewModelType
    ) {
        with(viewDataBinding) {
            with(np) {
                adapter = slotMachinePagerAdapter
                isUserInputEnabled = false
            }

            with(sv) {
                setFactor(1000f)
                setActionUpListener(onActionUpListener)
            }
        }
    }

    override fun onWillDetachViewModel(
        viewDataBinding: FragmentHomeBinding,
        viewModel: HomeViewModelType
    ) {
        with(viewDataBinding) {
            with(np) {
                np.adapter = null
            }
        }
    }

    companion object : FragmentLauncher<HomeFragment>() {
        override val fragmentClass: KClass<HomeFragment>
            get() = HomeFragment::class
    }
}