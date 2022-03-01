package com.anseolab.lotty.view.model

import com.anseolab.lotty.view.base.UiModel
import com.anseolab.lotty.view.base.ViewType

data class RecentAddressUiModel(
    val address: String
): UiModel {
    override val identifier: Any
        get() = address
    override val viewType: ViewType
        get() = RecentLotteryViewType.ITEM
}