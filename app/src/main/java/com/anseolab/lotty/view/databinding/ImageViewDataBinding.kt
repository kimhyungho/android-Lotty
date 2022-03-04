package com.anseolab.lotty.view.databinding

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@SuppressLint("CheckResult")
@BindingAdapter(
    value = [
        "imgSrc",
        "isCircular"
    ],
    requireAll = false
)
fun ImageView.bindSrc(
    src: String?,
    isCircular: Boolean? = null
) {
    Glide.with(this)
        .load(src)
        .apply {
            if(isCircular == true) {
                apply(RequestOptions.circleCropTransform())
            }
        }
        .into(this)
}

@SuppressLint("CheckResult")
@BindingAdapter(
    value = [
        "imgSrc",
        "isCircular"
    ],
    requireAll = false
)
fun ImageView.bindSrc(
    imgSrc: Drawable?,
    isCircular: Boolean? = null
) {
    Glide.with(this)
        .load(imgSrc)
        .apply {
            if(isCircular == true) {
                apply(RequestOptions.circleCropTransform())
            }
        }
        .into(this)
}
