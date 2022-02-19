package com.anseolab.lotty.view.detail.mapper

import android.graphics.Color
import com.anseolab.lotty.mapper.StateMapper
import com.anseolab.lotty.view.detail.DetailViewModel
import com.anseolab.lotty.view.model.LotteryUiModel

object LotteryStateMapper: StateMapper<DetailViewModel.State, LotteryUiModel> {
    override fun mapToView(state: DetailViewModel.State): LotteryUiModel {
        val item = state.lottery
        return LotteryUiModel(
            drwNo = item?.drwNo ?: 0,
            totSellamnt = item?.totSellamnt ?: 0,
            returnValue = (item?.returnValue ?: "fail") == "success",
            drwNoDate = item?.drwNoDate.toString(),
            firstWinamnt = item?.firstWinamnt ?: 0,
            firstAccumamnt = item?.firstAccumamnt ?: 0,
            drwtNo1 = item?.drwtNo1 ?: 0,
            drwtNo2 = item?.drwtNo2 ?: 0,
            drwtNo3 = item?.drwtNo3 ?: 0,
            drwtNo4 = item?.drwtNo4 ?: 0,
            drwtNo5 = item?.drwtNo5 ?: 0,
            drwtNo6 = item?.drwtNo6 ?: 0,
            bnusNo = item?.bnusNo ?: 0,
            firstPrzwnerCo = item?.firstPrzwnerCo ?: 0,
            isExpanded = true,
            drwtNo1Color = setColor(item?.drwtNo1),
            drwtNo2Color = setColor(item?.drwtNo2),
            drwtNo3Color = setColor(item?.drwtNo3),
            drwtNo4Color = setColor(item?.drwtNo4),
            drwtNo5Color = setColor(item?.drwtNo5),
            drwtNo6Color = setColor(item?.drwtNo6),
            bonusColor = setColor(item?.bnusNo)
        )
    }

    private fun setColor(drtwNo: Int?): Int {
        return when(drtwNo) {
            in 1..10 -> Color.parseColor("#FBC500")
            in 11..20 -> Color.parseColor("#69C8F2")
            in 21..30 -> Color.parseColor("#FF7272")
            in 31..40 -> Color.parseColor("#AAAAAA")
            in 41..45 -> Color.parseColor("#AFD840")
            else -> Color.parseColor("#FFFFFF")
        }
    }

    override fun diff(old: DetailViewModel.State, new: DetailViewModel.State): Boolean {
        return old.lottery === new.lottery
    }
}