package com.anseolab.lotty.providers.resource

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    @ApplicationContext
    private val applicationContext: Context
): ResourceProvider{
    private val resources = applicationContext.resources

    override fun getString(resId: Int): String {
        return resources.getString(resId)
    }

    override fun getFont(resId: Int): Typeface {
        return ResourcesCompat.getFont(applicationContext, resId)!!
    }

    override fun getColor(resId: Int): Int {
        return ContextCompat.getColor(applicationContext, resId)
    }

    override fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(applicationContext, resId)
    }
}