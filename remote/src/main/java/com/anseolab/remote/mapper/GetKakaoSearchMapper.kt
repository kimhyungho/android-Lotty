package com.anseolab.remote.mapper

import com.anseolab.data.model.KakaoStoreData
import com.anseolab.remote.model.response.kakao.GetKakaoSearchResponse

object GetKakaoSearchMapper : Mapper<GetKakaoSearchResponse, List<KakaoStoreData>> {
    override fun mapToData(from: GetKakaoSearchResponse): List<KakaoStoreData> {
        return from.documents.map {
            KakaoStoreData(
                id = it.id.toLong(),
                addressName = it.address_name,
                phone = it.phone,
                place_name = it.place_name,
                place_url = it.place_url,
                road_address_name = it.road_address_name,
                x = it.x.toDouble(),
                y = it.y.toDouble()
            )
        }
    }
}