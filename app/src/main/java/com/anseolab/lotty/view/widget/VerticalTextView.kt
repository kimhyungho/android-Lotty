package com.anseolab.lotty.view.widget

import android.content.Context
import android.graphics.Canvas
import android.text.BoringLayout
import android.text.Layout
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.withSave


class VerticalTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {
    private var topDown = false

    init {
        val gravity = gravity
        topDown =
            if (Gravity.isVertical(gravity) && (gravity and Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
                setGravity((gravity and Gravity.HORIZONTAL_GRAVITY_MASK) or Gravity.TOP)
                false
            } else {
                true
            }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }

    override fun onDraw(canvas: Canvas) {
        val textPaint = paint
        textPaint.color = currentTextColor
        textPaint.drawableState = drawableState

        canvas.save()

        if(topDown) {
            canvas.translate(width.toFloat(), 0f)
            canvas.rotate(90f)
        } else {
            canvas.translate(0f, height.toFloat())
            canvas.rotate(-90f)
        }

        canvas.translate(compoundPaddingLeft.toFloat(), extendedPaddingTop.toFloat())
        layout.draw(canvas)
        canvas.restore()
    }
}