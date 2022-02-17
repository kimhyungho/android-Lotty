package com.anseolab.lotty.view.widget

import android.R
import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatTextView

class CheckableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle), Checkable {

    var mChecked: Boolean = false


    override fun setChecked(checked: Boolean) {
        mChecked = checked
        refreshDrawableState()
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        mChecked = !mChecked
        refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }

        return drawableState
    }

    companion object {
        val CHECKED_STATE_SET: IntArray = intArrayOf(R.attr.state_checked)
    }

}