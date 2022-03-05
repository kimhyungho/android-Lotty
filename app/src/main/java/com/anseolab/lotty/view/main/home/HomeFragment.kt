package com.anseolab.lotty.view.main.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
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

    private val _viewModel: HomeViewModel by viewModels()
    override val viewModel: HomeViewModelType get() = _viewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.B600)

        with(viewDataBinding) {
            np.adapter = slotMachinePagerAdapter
            np.isUserInputEnabled = false
            sv.setFactor(1000f)
            sv.setActionUpListener(object : StretchTopNestedScrollView.OnActionUpListener {
                override fun onActionUpEvent() {
                    viewDataBinding.np.setItem(viewDataBinding.np.currentItem + 5, 1000)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object : FragmentLauncher<HomeFragment>() {
        override val fragmentClass: KClass<HomeFragment>
            get() = HomeFragment::class
    }
}