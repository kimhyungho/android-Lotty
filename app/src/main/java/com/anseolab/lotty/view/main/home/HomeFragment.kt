package com.anseolab.lotty.view.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentHomeBinding
import com.anseolab.lotty.view.adapter.BannerPagerAdapter
import com.anseolab.lotty.view.adapter.SlotMachinePagerAdapter
import com.anseolab.lotty.view.alert.scanner.ScannerAlertDialog
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
    private val bannerPagerAdapter = BannerPagerAdapter().apply {
        mListener = object: BannerPagerAdapter.Listener {
            override fun onItemClick(url: String) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.B400)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.B400)
    }

    private val onActionUpListener by lazy {
        object : StretchTopNestedScrollView.OnActionUpListener {
            override fun onActionUpEvent() {
                val nextItem = viewDataBinding.np.currentItem + 7

                viewModel.input.onPageStateChange(nextItem)
                viewDataBinding.np.setItem(
                    nextItem,
                    StretchTopNestedScrollView.SLOT_MACHINE_DURATION
                )
            }
        }
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            viewModel.input.onPageChange(position % 3)
        }
    }

    private var globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    private val _viewModel: HomeViewModel by viewModels()
    override val viewModel: HomeViewModelType get() = _viewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onWillAttachViewModel(
        viewDataBinding: FragmentHomeBinding,
        viewModel: HomeViewModelType
    ) {
        with(viewDataBinding) {
            with(vpBanner) {
                adapter = bannerPagerAdapter
                registerOnPageChangeCallback(onPageChangeCallback)

            }

            with(np) {
                adapter = slotMachinePagerAdapter
                isUserInputEnabled = false
            }

            with(sv) {
                setFactor(1000f)
                setActionUpListener(onActionUpListener)
            }

            with(sv.getTopView()!!) {
                globalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        val topView = viewDataBinding.sv.getTopView()!!

                        if (topView.height != 0) {
                            topView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            viewDataBinding.sv.setNormalHeight(topView.height)
                        }
                    }
                }

                viewTreeObserver?.addOnGlobalLayoutListener(globalLayoutListener)
            }

            btnAround.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.animate()
                            .setDuration(100)
                            .scaleX(0.95f)
                            .scaleY(0.95f)
                            .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        mListener?.onAroundButtonClick()
                        v.animate()
                            .setDuration(100)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start()
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        v.animate()
                            .setDuration(100)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start()
                    }
                }
                true
            }

            btnSearch.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.animate()
                            .setDuration(100)
                            .scaleX(0.9f)
                            .scaleY(0.9f)
                            .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        mListener?.onSearchButtonClick()
                        v.animate()
                            .setDuration(100)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start()
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        v.animate()
                            .setDuration(100)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start()
                    }
                }
                true
            }

            btnQr.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.animate()
                            .setDuration(100)
                            .scaleX(0.9f)
                            .scaleY(0.9f)
                            .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        ScannerAlertDialog.getInstance()
                            .show(childFragmentManager, ScannerAlertDialog.name)
                        v.animate()
                            .setDuration(100)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start()
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        v.animate()
                            .setDuration(100)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start()
                    }
                }
                true
            }

            btnCreate.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.animate()
                            .setDuration(100)
                            .scaleX(0.9f)
                            .scaleY(0.9f)
                            .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        mListener?.onCreateButtonClick()
                        v.animate()
                            .setDuration(100)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start()
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        v.animate()
                            .setDuration(100)
                            .scaleX(1f)
                            .scaleY(1f)
                            .start()
                    }
                }
                true
            }
        }
    }

    override fun onWillDetachViewModel(
        viewDataBinding: FragmentHomeBinding,
        viewModel: HomeViewModelType
    ) {
        with(viewDataBinding) {
            mListener = null
            vpBanner.unregisterOnPageChangeCallback(onPageChangeCallback)
            with(sv) {
                getTopView()!!.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
            }

            with(np) {
                adapter = null
            }
        }
    }

    companion object : FragmentLauncher<HomeFragment>() {

        var mListener: Listener? = null

        interface Listener {
            fun onAroundButtonClick()

            fun onSearchButtonClick()

            fun onCreateButtonClick()
        }

        override val fragmentClass: KClass<HomeFragment>
            get() = HomeFragment::class
    }
}