package com.anseolab.lotty.view.model

import com.anseolab.lotty.view.base.UiModel
import com.anseolab.lotty.view.base.ViewType

data class RecentLotteryUiModel(
    val drwNo: Long,
    val drwDate: String
): UiModel {
    override val identifier: Any
        get() = drwNo

    override val viewType: ViewType
        get() = RecentLotteryViewType.ITEM
}