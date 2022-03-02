package com.anseolab.lotty.view.databinding

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleOrGone")
fun View.bindVisibleOrGone(isVisible: Boolean?) {
    this.visibility = if (isVisible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("visibleOrInvisible")
fun View.bindVisibleOrInvisible(isVisible: Boolean?) {
    this.visibility = if (isVisible == true) View.VISIBLE else View.INVISIBLE
}

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("press")
fun View.bindPress(degree: Float?) {
    if (degree != null) this.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.animate()
                    .setDuration(100)
                    .scaleX(degree)
                    .scaleY(degree)
                    .start()
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
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