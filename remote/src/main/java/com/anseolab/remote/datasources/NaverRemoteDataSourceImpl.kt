package com.anseolab.remote.datasources

import android.util.Log
import com.anseolab.data.datasources.remote.NaverRemoteDataSource
import com.anseolab.data.model.StoreData
import com.anseolab.remote.mapper.GetSearchMapper
import com.anseolab.remote.retrofit.api.naver.NaverApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NaverRemoteDataSourceImpl @Inject constructor(
    private val naverApi: NaverApi
) : NaverRemoteDataSource {
    override fun search(query: String, x: Double, y: Double): Single<List<StoreData>> {
        val searchCoord = "${x};${y}"
        return naverApi.getSearch(query, searchCoord, 50)
            .map(GetSearchMapper::mapToData)
    }
}