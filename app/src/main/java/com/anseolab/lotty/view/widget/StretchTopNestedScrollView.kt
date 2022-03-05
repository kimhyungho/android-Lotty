package com.anseolab.lotty.view.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlin.math.abs
import curtains.OnTouchEventListener
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.OpenedNestedScrollView
import kotlinx.coroutines.delay


class StretchTopNestedScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : OpenedNestedScrollView(
    context, attrs, defStyle
) {

    private var mTopView: View? = null
    private var mBottomView: View? = null
    private var mNormalHeight = 0
    private var mMaxHeight: Int = 0
    private var mChangeListener: OnOverScrollChanged? = null
    private var mFactor = 1.6f
    private var mActionUpListener: OnActionUpListener? = null

    private interface OnTouchEventListener {
        fun onTouchEvent(ev: MotionEvent)
    }

    interface OnActionUpListener {
        fun onActionUpEvent()
    }

    fun setActionUpListener(listener: OnActionUpListener) {
        mActionUpListener = listener
    }

    fun setFactor(f: Float) {
        mFactor = f
        mTopView!!.postDelayed({
            mNormalHeight = 3
//            mNormalHeight = mTopView!!.height
            mMaxHeight = (mNormalHeight * mFactor).toInt()
        }, 50)
    }

//    fun getTopView(): View? {
//        return mTopView
//    }
//
//    fun getBottomView(): View? {
//        return mBottomView
//    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (childCount != 1) throw IllegalArgumentException("Root layout must be a LinearLayout, and only one child on this view!")

        val container = getChildAt(0) as? LinearLayout
            ?: throw IllegalArgumentException("Root layout is not a LinearLayout!")

        if (container.childCount != 2) throw IllegalArgumentException("Root LinearLayout's has not EXACTLY two Views!")

        mTopView = container.getChildAt(0)
        mBottomView = container.getChildAt(1)

        mTopView!!.postDelayed(Runnable {
//            mNormalHeight = mTopView!!.height
            mNormalHeight = 3
            mMaxHeight = (mNormalHeight * mFactor).toInt()
        }, 50)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        touchListener.onTouchEvent(ev!!)
        return super.onTouchEvent(ev)
    }

    interface OnOverScrollChanged {
        fun onChanged(v: Float)
    }

//    fun setChangeListener(changeListener: OnOverScrollChanged) {
//        mChangeListener = changeListener
//    }

    fun removeChangeListener() {
        mChangeListener = null
    }

    override fun openedOverScrollByCompat(
        deltaX: Int,
        deltaY: Int,
        scrollX: Int,
        scrollY: Int,
        scrollRangeX: Int,
        scrollRangeY: Int,
        maxOverScrollX: Int,
        maxOverScrollY: Int,
        isTouchEvent: Boolean
    ): Boolean {
        if (scrollY == 0) {
            if (deltaY < 0 && mTopView!!.layoutParams.height + abs(deltaY) > mMaxHeight) {
                mTopView!!.layoutParams.height = mMaxHeight
            } else if (deltaY < 0 && mTopView!!.layoutParams.height + abs(deltaY) <= mMaxHeight) {
                mTopView!!.layoutParams.height += abs(deltaY * 0.5).toInt()
            } else if (deltaY > 0 && mTopView!!.layoutParams.height - abs(deltaY) < mNormalHeight) {
                mTopView!!.layoutParams.height = mNormalHeight
            } else if (deltaY > 0 && mTopView!!.layoutParams.height - abs(deltaY) >= mNormalHeight) {
                mTopView!!.layoutParams.height -= abs(deltaY * 0.5).toInt()
            }
        }

        mChangeListener?.onChanged(
            (mMaxHeight - mTopView!!.layoutParams.height.toFloat()) / (mMaxHeight - mNormalHeight)
        )

        if (deltaY != 0 && scrollY == 0) {
            mTopView!!.requestLayout()
            mBottomView!!.requestLayout()
        }

        if (mTopView!!.layoutParams.height == mNormalHeight) {
            super.overScrollBy(
                deltaX, deltaY, scrollX,
                scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent
            )
        }
        return true
    }

    private val touchListener = object : OnTouchEventListener {
        override fun onTouchEvent(ev: MotionEvent) {
            if (ev.action == MotionEvent.ACTION_UP) {
                mActionUpListener?.onActionUpEvent()
                if (mTopView != null && mTopView!!.height > mNormalHeight) {
                    val animation = ResetAnimation(mTopView!!, mNormalHeight + 200)
                    animation.duration = 150
                    animation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            val animation2 = ResetAnimation(mTopView!!, mNormalHeight)
                            animation2.startOffset = 1500
                            animation2.duration = 150
                            mTopView!!.startAnimation(animation2)
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                        }
                    })
                    mTopView!!.startAnimation(animation)

                }
            }
        }
    }

    inner class ResetAnimation internal constructor(var mView: View, var targetHeight: Int) :
        Animation() {
        private var originalHeight: Int = mView.height
        private var extraHeight: Int = targetHeight - originalHeight

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            val newHeight = (targetHeight - extraHeight * (1 - interpolatedTime)).toInt()
            mView.layoutParams.height = newHeight
            mView.requestLayout()

//            if (mChangeListener != null) mChangeListener?.onChanged(
//                (mMaxHeight - mTopView!!.layoutParams.height.toFloat()) / (mMaxHeight - mNormalHeight)
//            )
        }
    }

}