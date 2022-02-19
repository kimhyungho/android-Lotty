package com.anseolab.data.mapper

import com.anseolab.data.model.LotteryData
import com.anseolab.domain.model.Lottery

object LotteriesMapper : Mapper<List<LotteryData>, List<Lottery>> {
    override fun mapToDomain(from: List<LotteryData>): List<Lottery> {
        return from.map {
            Lottery(
                totSellamnt = it.totSellamnt,
                returnValue = it.returnValue,
                drwNoDate = it.drwNoDate,
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
                drwNo = it.drwNo
            )
        }
    }
}