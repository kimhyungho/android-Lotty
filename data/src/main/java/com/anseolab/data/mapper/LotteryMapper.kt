package com.anseolab.data.mapper

import com.anseolab.data.model.LotteryData
import com.anseolab.domain.model.Lottery

object LotteryMapper : Mapper<LotteryData, Lottery> {
    override fun mapToDomain(from: LotteryData): Lottery {
        return Lottery(
            totSellamnt = from.totSellamnt,
            returnValue = from.returnValue,
            drwNoDate = from.drwNoDate,
            firstWinamnt = from.firstWinamnt,
            firstAccumamnt = from.firstAccumamnt,
            drwtNo1 = from.drwtNo1,
            drwtNo2 = from.drwtNo2,
            drwtNo3 = from.drwtNo3,
            drwtNo4 = from.drwtNo4,
            drwtNo5 = from.drwtNo5,
            drwtNo6 = from.drwtNo6,
            bnusNo = from.bnusNo,
            firstPrzwnerCo = from.firstPrzwnerCo,
            drwNo = from.drwNo ?: 0
        )
    }
}