package com.anseolab.lotty.view.main.random.mapper

import android.graphics.Color
import com.anseolab.lotty.mapper.StateMapper
import com.anseolab.lotty.view.main.random.RandomViewModel
import com.anseolab.lotty.view.model.DrwtNoUiModel

object DrwtNoStateMapper : StateMapper<RandomViewModel.State, DrwtNoUiModel> {
    override fun mapToView(state: RandomViewModel.State): DrwtNoUiModel {
        val drwtNo = state.drwtNo
        return DrwtNoUiModel(
            id = drwtNo?.id ?: 0,
            drwtNo1 = drwtNo?.drwtNo1 ?: 0,
            drwtNo2 = drwtNo?.drwtNo2 ?: 0,
            drwtNo3 = drwtNo?.drwtNo3 ?: 0,
            drwtNo4 = drwtNo?.drwtNo4 ?: 0,
            drwtNo5 = drwtNo?.drwtNo5 ?: 0,
            drwtNo6 = drwtNo?.drwtNo6 ?: 0,
            bnusNo = drwtNo?.bnusNo ?: 0,
            drwtNo1Color = setColor(drwtNo?.drwtNo1),
            drwtNo2Color = setColor(drwtNo?.drwtNo2),
            drwtNo3Color = setColor(drwtNo?.drwtNo3),
            drwtNo4Color = setColor(drwtNo?.drwtNo4),
            drwtNo5Color = setColor(drwtNo?.drwtNo5),
            drwtNo6Color = setColor(drwtNo?.drwtNo6),
            bonusColor = setColor(drwtNo?.bnusNo)
        )
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
        return old.drwtNo === new.drwtNo
    }
}