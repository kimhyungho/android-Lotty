package com.anseolab.lotty.view.databinding

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.anseolab.lotty.view.base.BaseRecyclerViewAdapter
import com.anseolab.lotty.view.base.UiModel

@BindingAdapter("list")
fun <E : UiModel> ViewPager2.bindList(items: List<E>?) {
    this.bindedAdapter?.submitList(items)
    this.currentItem = Int.MAX_VALUE / 2
}


val ViewPager2.bindedAdapter: BaseRecyclerViewAdapter<*>?
    get() = this.adapter as? BaseRecyclerViewAdapter<*>

fun ViewPager2.setItem(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width // Default value taken from getWidth() from ViewPager2 view
) {
    val pxToDrag: Int = pagePxWidth * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) { beginFakeDrag() }
        override fun onAnimationEnd(animation: Animator?) { endFakeDrag() }
        override fun onAnimationCancel(animation: Animator?) { /* Ignored */ }
        override fun onAnimationRepeat(animation: Animator?) { /* Ignored */ }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}