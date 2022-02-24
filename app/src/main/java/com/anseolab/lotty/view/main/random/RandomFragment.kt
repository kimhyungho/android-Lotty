package com.anseolab.lotty.view.main.random

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.anseolab.lotty.R
import com.anseolab.lotty.databinding.FragmentRandomBinding
import com.anseolab.lotty.view.base.FragmentLauncher
import com.anseolab.lotty.view.base.ViewModelFragment
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onWillAttachViewModel(
        viewDataBinding: FragmentRandomBinding,
        viewModel: RandomViewModelType
    ) {
        super.onWillAttachViewModel(viewDataBinding, viewModel)

        with(viewDataBinding) {
            layoutTouch.setOnTouchListener { _, event ->
                val x = (event.x / viewDataBinding.root.width).toDouble()
                val y = (event.y / viewDataBinding.root.height).toDouble()
                startAnimation(x, y)
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun startAnimation(x: Double, y: Double) {
        val party = Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            size = listOf(Size(36), Size(24), Size(18)),
            shapes = listOf(
                Shape.DrawableShape(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_coin_36x36
                    )!!, false
                )
            ),
            spread = 360,
            emitter = Emitter(duration = 300, TimeUnit.MILLISECONDS).max(30),
            position = Position.Relative(x, y),
            fadeOutEnabled = false,
            rotation = Rotation(false),
            timeToLive = 5000L,
        )

        viewDataBinding.kv.start(party)
    }

    companion object : FragmentLauncher<RandomFragment>() {
        override val fragmentClass: KClass<RandomFragment>
            get() = RandomFragment::class
    }
}