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

    lateinit var stretchTopNestedScrollView: StretchTopNestedScrollView


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.B600)

        with(viewDataBinding) {

            btnQr.setOnTouchListener { v, event ->
                when (event.action) {

                }

                true
            }

            stretchTopNestedScrollView = sv
            val bottomView = sv.getBottomView()
            stretchTopNestedScrollView.setFactor(1000f)

            stretchTopNestedScrollView.setChangeListener(object :
                StretchTopNestedScrollView.onOverScrollChanged {
                override fun onChanged(v: Float) {

                }
            })
        }
    }


    companion object : FragmentLauncher<HomeFragment>() {
        override val fragmentClass: KClass<HomeFragment>
            get() = HomeFragment::class
    }
}