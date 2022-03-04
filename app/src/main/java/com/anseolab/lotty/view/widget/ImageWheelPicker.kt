package com.anseolab.lotty.view.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.setPadding
import com.anseolab.lotty.R
import io.reactivex.rxjava3.core.Flowable
import kotlin.math.abs

class ImageWheelPicker @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(
    context,
    attr,
    defStyleAttr
) {

    private var stopRotate: Boolean = false
    private val items: MutableList<Drawable> = mutableListOf()
    private var containerView: LinearLayout? = null
    private var imageView: ImageView? = null
    private var imageHeight: Int = 108
    private var imageWidth: Int = 108
    private var currentItemIndex = 0

    fun setItems(items: List<Drawable>) {
        this.items.addAll(items)
        if (this.items.isNotEmpty()) {
            val randomIndex = (0 until this.items.size).random()
            currentItemIndex = randomIndex
            imageView?.setImageDrawable(this.items[currentItemIndex])
        }
    }

    fun setStopRotate(boolean: Boolean) {
        stopRotate = boolean
    }

    fun setImageHeight(dp: Int) {
        this.imageHeight =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                resources.displayMetrics
            ).toInt()
    }

    fun setImageWidth(dp: Int) {
        this.imageWidth =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                resources.displayMetrics
            ).toInt()
    }

    fun setRotate(degree: Float) {
        if (!stopRotate) {
            val height = containerView!!.height - imageView!!.height
            imageView!!.y = (height) - (height) * degree
            imageView!!.alpha = (1 - abs(degree * 2 - 1))

            if (degree <= 0 || degree >= 1) {
                currentItemIndex = (currentItemIndex + 1) % items.size
                imageView!!.setImageDrawable(items[currentItemIndex])
            }
        }
    }

    fun startAnimation() {
        stopRotate = true

        imageView!!.animate()
            .setDuration(500)
            .yBy(imageView!!.height.toFloat() * 3)
            .y(imageView!!.height.toFloat())
            .alphaBy(0f)
            .alpha(1f)
            .start()

        stopRotate = false
    }

    fun removeItems() {
        items.clear()
    }

    fun setImageSize(width: Int, height: Int) {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

    }

    init {
        containerView = LinearLayout(context).apply {
            layoutParams = LayoutParams(imageWidth, imageHeight * 3)

            imageView = ImageView(context).apply {
                layoutParams = LayoutParams(imageHeight, imageWidth)
            }
            addView(imageView)
        }

        addView(containerView)
    }
}