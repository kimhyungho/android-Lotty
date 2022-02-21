package com.anseolab.remote.mapper

import android.util.Log
import com.anseolab.data.model.StoreData
import com.anseolab.remote.model.response.naver.GetSearchResponse

object GetSearchMapper: Mapper<GetSearchResponse, List<StoreData>> {
    override fun mapToData(from: GetSearchResponse): List<StoreData> {
        return from.result.place.list.map {
            StoreData(
                id = it.id ?: 0,
                name = it.name ?: "",
                address = it.address ?: "",
                roadAddress = it.roadAddress ?: "",
                shortAddress = it.shortAddress ?: listOf(),
                x = it.x?.toDouble() ?: 0.0,
                y = it.y?.toDouble() ?: 0.0
            )
        }
    }
}