package com.anseolab.data.repositories

import com.anseolab.data.datasources.remote.NaverRemoteDataSource
import com.anseolab.data.mapper.StoreMapper
import com.anseolab.domain.model.Store
import com.anseolab.domain.repositories.NaverRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val naverRemoteDataSource: NaverRemoteDataSource
) : NaverRepository {
    override fun search(query: String, x: Double, y: Double): Single<List<Store>> {
        return naverRemoteDataSource.search(query, x, y)
            .map(StoreMapper::mapToDomain)
    }
}