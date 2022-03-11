package com.anseolab.lotty.providers.resource

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.*

interface ResourceProvider {
    fun getString(@StringRes resId: Int): String

    fun getFont(@FontRes resId: Int): Typeface

    @ColorInt
    fun getColor(@ColorRes resId: Int): Int

    fun getDrawable(@DrawableRes resId: Int): Drawable?

    fun getStringArray(@ArrayRes resId: Int): Array<String>
}