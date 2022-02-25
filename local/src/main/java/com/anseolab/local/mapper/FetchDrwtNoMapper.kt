package com.anseolab.local.mapper

import com.anseolab.data.model.DrwtNoData
import com.anseolab.local.model.entity.DrwtNoEntity

object FetchDrwtNoMapper: Mapper<List<DrwtNoEntity>, List<DrwtNoData>> {
    override fun mapToData(from: List<DrwtNoEntity>): List<DrwtNoData> {
        return from.map {
            DrwtNoData(
                id = it.id,
                drwtNo1 = it.drwtNo1,
                drwtNo2 = it.drwtNo2,
                drwtNo3 = it.drwtNo3,
                drwtNo4 = it.drwtNo4,
                drwtNo5 = it.drwtNo5,
                drwtNo6 = it.drwtNo6,
                bnusNo = it.bnusNo
            )
        }
    }
}