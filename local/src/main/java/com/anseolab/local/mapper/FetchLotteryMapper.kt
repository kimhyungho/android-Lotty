package com.anseolab.local.mapper

import com.anseolab.data.model.LotteryData
import com.anseolab.local.model.entity.LotteryEntity
import java.time.LocalDate

object FetchLotteryMapper: Mapper<List<LotteryEntity>, List<LotteryData>> {
    override fun mapToData(from: List<LotteryEntity>): List<LotteryData> {
        return from.map {
            LotteryData(
                totSellamnt = it.totSellamnt,
                returnValue = it.returnValue,
                drwNoDate = LocalDate.parse(it.drwNoDate),
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