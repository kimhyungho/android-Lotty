package com.anseolab.lotty.view.main.random.mapper

import android.graphics.Color
import com.anseolab.lotty.mapper.StateMapper
import com.anseolab.lotty.view.main.random.RandomViewModel
import com.anseolab.lotty.view.model.DrwtNoUiModel

object RecentDrwtNosStateMapper : StateMapper<RandomViewModel.State, List<DrwtNoUiModel>> {
    override fun mapToView(state: RandomViewModel.State): List<DrwtNoUiModel> {
        return state.recentDrwtNo.map {
            DrwtNoUiModel(
                id = it.id,
                drwtNo1 = it.drwtNo1,
                drwtNo2 = it.drwtNo2,
                drwtNo3 = it.drwtNo3,
                drwtNo4 = it.drwtNo4,
                drwtNo5 = it.drwtNo5,
                drwtNo6 = it.drwtNo6,
                bnusNo = it.bnusNo,
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
        return when (drtwNo) {
            in 1..10 -> Color.parseColor("#FBC500")
            in 11..20 -> Color.parseColor("#69C8F2")
            in 21..30 -> Color.parseColor("#FF7272")
            in 31..40 -> Color.parseColor("#AAAAAA")
            in 41..45 -> Color.parseColor("#AFD840")
            else -> Color.parseColor("#FFFFFF")
        }
    }

    override fun diff(old: RandomViewModel.State, new: RandomViewModel.State): Boolean {
        return old.recentDrwtNo === new.recentDrwtNo
    }
}