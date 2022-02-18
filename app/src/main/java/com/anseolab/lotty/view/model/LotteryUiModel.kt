package com.anseolab.lotty.view.model

import com.anseolab.lotty.view.base.UiModel
import com.anseolab.lotty.view.base.ViewType
import java.time.LocalDate

data class LotteryUiModel(
    val drwNo : Long,
    val totSellamnt: Long?,
    val returnValue: String,
    val drwNoDate: String?,
    val firstWinamnt: Long?,
    val firstAccumamnt: Long?,
    val drwtNo1: Int?,
    val drwtNo2: Int?,
    val drwtNo3: Int?,
    val drwtNo4: Int?,
    val drwtNo5: Int?,
    val drwtNo6: Int?,
    val bnusNo: Int?,
    val firstPrzwnerCo: Long?,
    val isExpanded: Boolean,
    val drwtNo1Color: Int,
    val drwtNo2Color: Int,
    val drwtNo3Color: Int,
    val drwtNo4Color: Int,
    val drwtNo5Color: Int,
    val drwtNo6Color: Int,
    val bonusColor: Int
    ): UiModel {
    override val identifier: Any
        get() = drwNo
    override val viewType: ViewType
        get() = LotteryViewType.ITEM
}