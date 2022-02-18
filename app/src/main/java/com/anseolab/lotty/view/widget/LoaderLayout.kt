package com.anseolab.lotty.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.withStyledAttributes
import androidx.databinding.BindingAdapter
import com.anseolab.lotty.R

class LoaderLayout @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(
    context,
    attr,
    defStyleAttr
) {
    private var progressBar: ProgressBar

    private var _isLoading: Boolean = false
    var isLoading: Boolean
        get() = _isLoading
        set(value) {
            val old = _isLoading
            _isLoading = value

            if (old != value) {
                refreshProgress()
            }
        }
    init {
        context.withStyledAttributes(attr, R.styleable.LoaderLayout, defStyleAttr, 0) {
            _isLoading = getBoolean(R.styleable.LoaderLayout_isLoading, false)
        }
        inflate(context, R.layout.layout_loader_progress, this)
        progressBar = findViewById(R.id.pgb_loading)
        refreshProgress()
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        if (child !is ProgressBar) {
            this.progressBar.bringToFront()
            return
        }
    }

    private fun refreshProgress() {
        this.progressBar.visibility = if (this.isLoading) View.VISIBLE else View.INVISIBLE
        this.isClickable = this.isLoading
    }
}

@BindingAdapter("isLoading")
fun LoaderLayout.bindLoading(isLoading: Boolean?) {
    this.isLoading = isLoading ?: false
}