package com.anseolab.lotty.view.main.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentHomeBinding
import com.anseolab.lotty.view.adapter.SlotMachinePagerAdapter
import com.anseolab.lotty.view.base.BaseFragment
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.databinding.setItem
import com.anseolab.lotty.view.widget.StretchTopNestedScrollView
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
import kotlin.reflect.KClass

@AndroidEntryPoint
class HomeFragment : ViewModelFragment<FragmentHomeBinding, HomeViewModelType>(
    R.layout.fragment_home
) {

    override val showBackButton: Boolean = false

    private val slotMachinePagerAdapter = SlotMachinePagerAdapter()

    private var currentV = 0.0f
    private var oldV = 1.1f

    //    private var degree = 1.0f
    private val _viewModel: HomeViewModel by viewModels()
    override val viewModel: HomeViewModelType get() = _viewModel


    private val onActionUpListener by lazy {
        object : StretchTopNestedScrollView.OnActionUpListener {
            override fun onActionUpEvent() {
//                viewDataBinding.np.startAnimation()
            }
        }
    }

    private val overScrollChangedListener by lazy {
        object : StretchTopNestedScrollView.OnOverScrollChanged {
            override fun onChanged(v: Float) {
                currentV = v
                if (currentV < oldV && abs(currentV - oldV) > 0.0005f) {
                    viewDataBinding.np.setItem(viewDataBinding.np.currentItem - 1, 1000, 1000)
                    oldV = currentV
                } else if (currentV > oldV && abs(currentV - oldV) > 0.0005f) {
                    viewDataBinding.np.setItem(viewDataBinding.np.currentItem + 1, 1000, 1000)
                    oldV = currentV
                }

            }
        }
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)

            Log.d("kkkk", slotMachinePagerAdapter.itemCount.toString())

            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                when (viewDataBinding.np.currentItem) {
                    slotMachinePagerAdapter.itemCount - 1 -> {
                        viewDataBinding.np.setCurrentItem(1, false)
                    }

                    0 -> {
                        viewDataBinding.np.setCurrentItem(
                            slotMachinePagerAdapter.itemCount - 2,
                            false
                        )
                    }
                }
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.B600)

        with(viewDataBinding) {
            np.adapter = slotMachinePagerAdapter

            sv.setFactor(1000f)
            sv.setChangeListener(overScrollChangedListener)


            np.registerOnPageChangeCallback(pageChangeCallback)

        }
    }

    override fun onDestroyView() {
        viewDataBinding.sv.removeChangeListener()
        super.onDestroyView()
    }

    companion object : FragmentLauncher<HomeFragment>() {
        override val fragmentClass: KClass<HomeFragment>
            get() = HomeFragment::class
    }
}