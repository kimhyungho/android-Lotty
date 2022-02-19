package com.anseolab.local.mapper

import com.anseolab.data.model.LotteryData
import com.anseolab.local.model.entity.LotteryEntity

object LotteryEntityMapper : Mapper<LotteryData, LotteryEntity> {
    override fun mapToData(from: LotteryData): LotteryEntity {
        return LotteryEntity(
            drwNo = from.drwNo ?: 0,
            totSellamnt = from.totSellamnt ?: 0,
            returnValue = from.returnValue,
            drwNoDate = if (from.drwNoDate == null) "1997-09-05" else from.drwNoDate.toString(),
            firstWinamnt = from.firstWinamnt ?: 0,
            firstAccumamnt = from.firstAccumamnt ?: 0,
            drwtNo1 = from.drwtNo1 ?: 0,
            drwtNo2 = from.drwtNo2 ?: 0,
            drwtNo3 = from.drwtNo3 ?: 0,
            drwtNo4 = from.drwtNo4 ?: 0,
            drwtNo5 = from.drwtNo5 ?: 0,
            drwtNo6 = from.drwtNo6 ?: 0,
            bnusNo = from.bnusNo ?: 0,
            firstPrzwnerCo = from.firstPrzwnerCo ?: 0
        )
    }
}