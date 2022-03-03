package com.anseolab.lotty.view.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.anseolab.lotty.R
import io.reactivex.rxjava3.core.Flowable

class ImageWheelPicker @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(
    context,
    attr,
    defStyleAttr
) {

    private val items: MutableList<Drawable> = mutableListOf()
    private var imageView : ImageView? = null

    fun setItems(items: List<Drawable>) {
        this.items.addAll(items)
    }

    fun setRotate(degree: Float) {
        if(imageView != null) imageView!!.alpha = degree
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
        imageView = ImageView(context)
        imageView?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_24x24))
        addView(imageView)
    }
}