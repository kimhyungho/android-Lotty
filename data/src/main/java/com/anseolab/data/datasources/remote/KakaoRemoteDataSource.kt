package com.anseolab.data.datasources.remote

import com.anseolab.data.model.KakaoStoreData
import io.reactivex.rxjava3.core.Single

interface KakaoRemoteDataSource {
    fun search(query: String, x: Double, y: Double): Single<List<KakaoStoreData>>
}