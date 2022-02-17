package com.anseolab.lotty.view.main.search.mapper

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
                drwNoDate = it.drwNoDate,
                firstWinamnt = it.firstWinamnt,
                drwtNo1 = it.drwtNo1,
                drwtNo2 = it.drwtNo2,
                drwtNo3 = it.drwtNo3,
                drwtNo4 = it.drwtNo4,
                drwtNo5 = it.drwtNo5,
                drwtNo6 = it.drwtNo6,
                bnusNo = it.bnusNo,
                firstPrzwnerCo = it.firstPrzwnerCo,
                isExpanded = state.expandedLotteries.contains(it.drwNo)
            )
        }
    }

    override fun diff(old: SearchViewModel.State, new: SearchViewModel.State): Boolean {
        return old.lotteries === new.lotteries && old.lotteryTrigger === new.lotteryTrigger && old.expandedLotteries == new.expandedLotteries
    }
}