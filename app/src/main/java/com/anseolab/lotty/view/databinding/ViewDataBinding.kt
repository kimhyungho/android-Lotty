package com.anseolab.lotty.view.databinding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleOrGone")
fun View.bindVisibleOrGone(isVisible: Boolean?) {
    this.visibility = if(isVisible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("visibleOrInvisible")
fun View.bindVisibleOrInvisible(isVisible: Boolean?) {
    this.visibility = if(isVisible == true) View.VISIBLE else View.INVISIBLE
}