package com.anseolab.lotty.view.model

import android.graphics.drawable.Drawable
import com.anseolab.lotty.view.base.UiModel
import com.anseolab.lotty.view.base.ViewType

data class BannerUiModel(
    val src: Drawable,
    val url: String
): UiModel {
    override val identifier: Any
        get() = url
    override val viewType: ViewType
        get() = BannerViewType.ITEM
}