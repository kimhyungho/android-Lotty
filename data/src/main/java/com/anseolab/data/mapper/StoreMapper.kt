package com.anseolab.data.mapper

import com.anseolab.data.model.StoreData
import com.anseolab.domain.model.Store

object StoreMapper : Mapper<List<StoreData>, List<Store>> {
    override fun mapToDomain(from: List<StoreData>): List<Store> {
        return from.map {
            Store(
                id = it.id,
                name = it.name,
                address = it.address,
                roadAddress = it.roadAddress,
                shortAddress = it.shortAddress,
                x = it.x,
                y = it.y
            )
        }
    }
}