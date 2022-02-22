package com.anseolab.data.repositories

import com.anseolab.data.datasources.remote.KakaoRemoteDataSource
import com.anseolab.data.mapper.KakaoStoreMapper
import com.anseolab.domain.model.KakaoStore
import com.anseolab.domain.repositories.KakaoRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource
) : KakaoRepository {
    override fun search(query: String, x: Double, y: Double): Single<List<KakaoStore>> {
        return kakaoRemoteDataSource.search(query, x, y)
            .map(KakaoStoreMapper::mapToDomain)
    }
}