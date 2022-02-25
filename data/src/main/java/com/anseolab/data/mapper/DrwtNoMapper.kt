package com.anseolab.data.mapper

import com.anseolab.data.model.DrwtNoData
import com.anseolab.domain.model.DrwtNo

object DrwtNoMapper: Mapper<List<DrwtNoData>, List<DrwtNo>> {
    override fun mapToDomain(from: List<DrwtNoData>): List<DrwtNo> {
        return from.map {
            DrwtNo(
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