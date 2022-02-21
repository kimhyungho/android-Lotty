package com.anseolab.data.datasources.remote

import com.anseolab.data.model.StoreData
import io.reactivex.rxjava3.core.Single

interface NaverRemoteDataSource {
    fun search(query: String, x: Double, y: Double): Single<List<StoreData>>
}