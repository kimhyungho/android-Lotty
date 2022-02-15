package com.anseolab.lotty.utils.gilde

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

object GlideUtil {
    @JvmStatic
    fun clear(view: View) {
        if(view is RecyclerView) return
        if(view is ViewGroup) {
            for(child in view.children) {
                clear(child)
            }
        } else if(view is ImageView) {
            try {
                Glide.with(view).clear(view)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }
}