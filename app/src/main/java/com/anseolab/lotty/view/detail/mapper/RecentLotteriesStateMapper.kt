package com.anseolab.lotty.view.detail.mapper

import android.graphics.Color
import android.util.Log
import com.anseolab.lotty.mapper.StateMapper
import com.anseolab.lotty.view.detail.DetailViewModel
import com.anseolab.lotty.view.model.LotteryUiModel
import com.anseolab.lotty.view.model.RecentLotteryUiModel

object RecentLotteriesStateMapper: StateMapper<DetailViewModel.State, List<RecentLotteryUiModel>> {
    override fun mapToView(state: DetailViewModel.State): List<RecentLotteryUiModel> {
        return state.recentLotteries.map {
            RecentLotteryUiModel(
                drwNo = it.drwNo ?: 0,
                drwDate = it.drwNoDate.toString()
            )
        }
    }

    override fun diff(old: DetailViewModel.State, new: DetailViewModel.State): Boolean {
        return old.recentLotteries === new.recentLotteries
    }
}