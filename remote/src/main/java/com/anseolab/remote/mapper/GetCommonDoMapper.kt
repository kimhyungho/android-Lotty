package com.anseolab.remote.mapper

import com.anseolab.data.model.LotteryData
import com.anseolab.remote.model.response.dhlottery.GetCommonDoResponse
import java.time.LocalDate

object GetCommonDoMapper : Mapper<GetCommonDoResponse, LotteryData> {
    override fun mapToData(from: GetCommonDoResponse): LotteryData {
        return LotteryData(
            totSellamnt = from.totSellamnt,
            returnValue = from.returnValue,
            drwNoDate = LocalDate.parse(from.drwNoDate) ?: null,
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
            drwNo = from.drwNo
        )
    }
}