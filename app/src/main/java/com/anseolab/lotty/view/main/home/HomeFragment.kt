package com.anseolab.lotty.view.main.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentHomeBinding
import com.anseolab.lotty.view.base.BaseFragment
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.widget.StretchTopNestedScrollView
import com.jakewharton.rxbinding4.view.clicks
import kotlin.reflect.KClass

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    override val showBackButton: Boolean = false

    private val overScrollChangedListener by lazy {
        object : StretchTopNestedScrollView.OnOverScrollChanged {
            override fun onChanged(v: Float) {
                viewDataBinding.np.setRotate(v)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.B600)

        with(viewDataBinding) {
            sv.setFactor(1000f)
            sv.setChangeListener(overScrollChangedListener)
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