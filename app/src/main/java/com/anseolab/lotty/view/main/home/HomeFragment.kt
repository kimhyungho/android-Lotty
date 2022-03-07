package com.anseolab.lotty.view.main.home

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentHomeBinding
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

    private val globalLayoutListener by lazy {
        object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val topView = viewDataBinding.sv.getTopView()!!
                if (topView.height != 0) {
                    topView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    viewDataBinding.sv.setNormalHeight(topView.height)
                }
            }
        }
    }

    private val _viewModel: HomeViewModel by viewModels()
    override val viewModel: HomeViewModelType get() = _viewModel

    @SuppressLint("ClickableViewAccessibility")
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

            with(sv.getTopView()!!) {
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
                            .scaleX(0.95f)
                            .scaleY(0.95f)
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
                            .scaleX(0.95f)
                            .scaleY(0.95f)
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
                            .scaleX(0.95f)
                            .scaleY(0.95f)
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