package com.anseolab.lotty.view.main.random

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentRandomBinding
import com.anseolab.lotty.extensions.throttle
import com.anseolab.lotty.view.adapter.RecentDrwtNoListAdapter
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
import com.anseolab.lotty.view.main.home.HomeFragment
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Rotation
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

@AndroidEntryPoint
class RandomFragment : ViewModelFragment<FragmentRandomBinding, RandomViewModelType>(
    R.layout.fragment_random
) {
    private val _viewModel: RandomViewModel by viewModels()
    override val viewModel: RandomViewModelType get() = _viewModel

    private val recentDrwtNoListAdapter = RecentDrwtNoListAdapter().apply {
        listener = object: RecentDrwtNoListAdapter.Listener {
            override fun onRemoveClick(id: Int) {
                viewModel.input.onRemoveButtonClick(id)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onWillAttachViewModel(
        viewDataBinding: FragmentRandomBinding,
        viewModel: RandomViewModelType
    ) {
        super.onWillAttachViewModel(viewDataBinding, viewModel)

        with(viewDataBinding) {
            rvRecent.adapter = recentDrwtNoListAdapter

            layoutTouch.setOnTouchListener { _, event ->
                val x = (event.x / root.width).toDouble()
                val y = (event.y / root.height).toDouble()
                startAnimation(x, y)
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
                        viewModel.input.onCreateButtonClick()
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

            btnClear.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.animate()
                            .setDuration(100)
                            .scaleX(0.9f)
                            .scaleY(0.9f)
                            .start()
                    }
                    MotionEvent.ACTION_UP -> {
                        viewModel.input.onClearButtonClick()
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

    private fun startAnimation(x: Double, y: Double) {
        val party = Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            size = listOf(Size(36), Size(24), Size(18)),
            spread = 360,
            emitter = Emitter(duration = 300, TimeUnit.MILLISECONDS).max(30),
            position = Position.Relative(x, y),
            fadeOutEnabled = false,
            rotation = Rotation(true),
            timeToLive = 5000L,
            shapes = listOf(
                Shape.DrawableShape(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_coin_36x36
                    )!!, false
                )
            )
        )

//        viewDataBinding.kv.start(party)
    }

    override fun onDestroyView() {
        viewDataBinding.rvRecent.adapter = null
        super.onDestroyView()
    }

    companion object : FragmentLauncher<RandomFragment>() {
        override val fragmentClass: KClass<RandomFragment>
            get() = RandomFragment::class
    }
}