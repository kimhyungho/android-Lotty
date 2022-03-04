package com.anseolab.lotty.view.model

import android.graphics.drawable.Drawable
import com.anseolab.lotty.view.base.UiModel
import com.anseolab.lotty.view.base.ViewType

data class SlotMachineUiModel(
    val drawable: Drawable
) : UiModel {
    override val identifier: Any
        get() = drawable

    override val viewType: ViewType
        get() = SlotMachineViewType.ITEM
}