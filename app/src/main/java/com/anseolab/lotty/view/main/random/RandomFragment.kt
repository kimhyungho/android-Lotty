package com.anseolab.lotty.view.main.random

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentRandomBinding
import com.anseolab.lotty.extensions.getDrwNum
import com.anseolab.lotty.extensions.getNextSaturday
import com.anseolab.lotty.extensions.throttle
import com.anseolab.lotty.providers.resource.ResourceProvider
import com.anseolab.lotty.view.adapter.RecentDrwtNoListAdapter
import com.anseolab.lotty.view.alert.scanner.ScannerAlertDialog
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.main.home.HomeFragment
import com.jakewharton.rxbinding4.view.clicks
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView
import dagger.hilt.android.AndroidEntryPoint
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Rotation
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.reflect.KClass

@AndroidEntryPoint
class RandomFragment : ViewModelFragment<FragmentRandomBinding, RandomViewModelType>(
    R.layout.fragment_random
) {
    private val _viewModel: RandomViewModel by viewModels()
    override val viewModel: RandomViewModelType get() = _viewModel

    @Inject
    lateinit var resourceProvider: ResourceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onWillAttachViewModel(
        viewDataBinding: FragmentRandomBinding,
        viewModel: RandomViewModelType
    ) {
        super.onWillAttachViewModel(viewDataBinding, viewModel)
        with(viewDataBinding) {
            tvA1.setting()
            tvA2.setting()
            tvA3.setting()
            tvA4.setting()
            tvA5.setting()
            tvA6.setting()
            tvB1.setting()
            tvB2.setting()
            tvB3.setting()
            tvB4.setting()
            tvB5.setting()
            tvB6.setting()
            tvC1.setting()
            tvC2.setting()
            tvC3.setting()
            tvC4.setting()
            tvC5.setting()
            tvC6.setting()
            tvD1.setting()
            tvD2.setting()
            tvD3.setting()
            tvD4.setting()
            tvD5.setting()
            tvD6.setting()
            tvE1.setting()
            tvE2.setting()
            tvE3.setting()
            tvE4.setting()
            tvE5.setting()
            tvE6.setting()

            btnDraw.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.animate()
                            .setDuration(100)
                            .scaleX(0.9f)
                            .scaleY(0.9f)
                            .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        viewModel.input.onDrawButtonClick()
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


            with(viewModel.output) {
                with(viewDataBinding) {
                    aList.observe {
                        if (it[0] != 0) tvA1.setText(it[0].toString(), true)
                        if (it[1] != 0) tvA2.setText(it[1].toString(), true)
                        if (it[2] != 0) tvA3.setText(it[2].toString(), true)
                        if (it[3] != 0) tvA4.setText(it[3].toString(), true)
                        if (it[4] != 0) tvA5.setText(it[4].toString(), true)
                        if (it[5] != 0) tvA6.setText(it[5].toString(), true)
                    }

                    bList.observe {
                        if (it[0] != 0) tvB1.setText(it[0].toString(), true)
                        if (it[1] != 0) tvB2.setText(it[1].toString(), true)
                        if (it[2] != 0) tvB3.setText(it[2].toString(), true)
                        if (it[3] != 0) tvB4.setText(it[3].toString(), true)
                        if (it[4] != 0) tvB5.setText(it[4].toString(), true)
                        if (it[5] != 0) tvB6.setText(it[5].toString(), true)
                    }

                    cList.observe {
                        if (it[0] != 0) tvC1.setText(it[0].toString(), true)
                        if (it[1] != 0) tvC2.setText(it[1].toString(), true)
                        if (it[2] != 0) tvC3.setText(it[2].toString(), true)
                        if (it[3] != 0) tvC4.setText(it[3].toString(), true)
                        if (it[4] != 0) tvC5.setText(it[4].toString(), true)
                        if (it[5] != 0) tvC6.setText(it[5].toString(), true)
                    }

                    dList.observe {
                        if (it[0] != 0) tvD1.setText(it[0].toString(), true)
                        if (it[1] != 0) tvD2.setText(it[1].toString(), true)
                        if (it[2] != 0) tvD3.setText(it[2].toString(), true)
                        if (it[3] != 0) tvD4.setText(it[3].toString(), true)
                        if (it[4] != 0) tvD5.setText(it[4].toString(), true)
                        if (it[5] != 0) tvD6.setText(it[5].toString(), true)
                    }

                    eList.observe {
                        if (it[0] != 0) tvE1.setText(it[0].toString(), true)
                        if (it[1] != 0) tvE2.setText(it[1].toString(), true)
                        if (it[2] != 0) tvE3.setText(it[2].toString(), true)
                        if (it[3] != 0) tvE4.setText(it[3].toString(), true)
                        if (it[4] != 0) tvE5.setText(it[4].toString(), true)
                        if (it[5] != 0) tvE6.setText(it[5].toString(), true)
                    }

                }
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden) requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun TickerView.setting() {
        this.setCharacterLists(*resourceProvider.getStringArray(R.array.number_array))
        this.typeface = Typeface.DEFAULT_BOLD
    }


    companion object : FragmentLauncher<RandomFragment>() {
        override val fragmentClass: KClass<RandomFragment>
            get() = RandomFragment::class
    }
}