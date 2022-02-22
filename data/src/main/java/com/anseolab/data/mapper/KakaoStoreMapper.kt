package com.anseolab.data.mapper

import com.anseolab.data.model.KakaoStoreData
import com.anseolab.domain.model.KakaoStore

object KakaoStoreMapper: Mapper<List<KakaoStoreData>, List<KakaoStore>> {
    override fun mapToDomain(from: List<KakaoStoreData>): List<KakaoStore> {
        return from.map {
            KakaoStore(
                id = it.id,
                addressName = it.addressName,
                phone = it.phone,
                place_name = it.place_name,
                place_url = it.place_url,
                road_address_name = it.road_address_name,
                x = it.x,
                y= it.y
            )
        }
    }
}