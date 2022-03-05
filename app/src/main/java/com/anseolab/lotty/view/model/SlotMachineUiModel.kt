package com.anseolab.lotty.view.model

import android.graphics.drawable.Drawable
import com.anseolab.lotty.view.base.UiModel
import com.anseolab.lotty.view.base.ViewType

data class SlotMachineUiModel(
    val id: Int,
    val drawable: Drawable,
    val number: String = (1..45).random().toString(),
    val isLastModel: Boolean
) : UiModel {
    override val identifier: Any
        get() = drawable

    override val viewType: ViewType
        get() = SlotMachineViewType.ITEM
}