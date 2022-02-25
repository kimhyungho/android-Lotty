package com.anseolab.local.mapper

import com.anseolab.data.model.DrwtNoData
import com.anseolab.local.model.entity.DrwtNoEntity
import java.util.*

object DrwtNoEntityMapper : Mapper<DrwtNoData, DrwtNoEntity> {
    override fun mapToData(from: DrwtNoData): DrwtNoEntity {
        return DrwtNoEntity(
            drwtNo1 = from.drwtNo1,
            drwtNo2 = from.drwtNo2,
            drwtNo3 = from.drwtNo3,
            drwtNo4 = from.drwtNo4,
            drwtNo5 = from.drwtNo5,
            drwtNo6 = from.drwtNo6,
            bnusNo = from.bnusNo,
            createdAt = Date().time
        )
    }
}