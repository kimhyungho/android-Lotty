package com.anseolab.lotty.view.main.search.mapper

import android.graphics.Color
import com.anseolab.lotty.mapper.StateMapper
import com.anseolab.lotty.view.main.search.SearchViewModel
import com.anseolab.lotty.view.model.LotteryUiModel

object LotteryListStateMapper : StateMapper<SearchViewModel.State, List<LotteryUiModel>> {
    override fun mapToView(state: SearchViewModel.State): List<LotteryUiModel> {
        return state.lotteries.map {
            LotteryUiModel(
                drwNo = it.drwNo ?: 0,
                totSellamnt = it.totSellamnt,
                returnValue = it.returnValue,
                drwNoDate = it.drwNoDate.toString(),
                firstWinamnt = it.firstWinamnt,
                firstAccumamnt = it.firstAccumamnt,
                drwtNo1 = it.drwtNo1,
                drwtNo2 = it.drwtNo2,
                drwtNo3 = it.drwtNo3,
                drwtNo4 = it.drwtNo4,
                drwtNo5 = it.drwtNo5,
                drwtNo6 = it.drwtNo6,
                bnusNo = it.bnusNo,
                firstPrzwnerCo = it.firstPrzwnerCo,
                isExpanded = state.expandedLotteries.contains(it.drwNo),
                drwtNo1Color = setColor(it.drwtNo1),
                drwtNo2Color = setColor(it.drwtNo2),
                drwtNo3Color = setColor(it.drwtNo3),
                drwtNo4Color = setColor(it.drwtNo4),
                drwtNo5Color = setColor(it.drwtNo5),
                drwtNo6Color = setColor(it.drwtNo6),
                bonusColor = setColor(it.bnusNo)
            )
        }
    }

    private fun setColor(drtwNo: Int?): Int {
        return when(drtwNo) {
            in 1..10 -> Color.parseColor("#FBC500")
            in 11..20 -> Color.parseColor("#69C8F2")
            in 21..30 -> Color.parseColor("#FF7272")
            in 31..40 -> Color.parseColor("#AAAAAA")
            in 41..45 -> Color.parseColor("#AFD840")
            else -> Color.parseColor("#000000")
        }
    }

    override fun diff(old: SearchViewModel.State, new: SearchViewModel.State): Boolean {
        return old.lotteries === new.lotteries && old.lotteryTrigger === new.lotteryTrigger && old.expandedLotteries == new.expandedLotteries
    }
}