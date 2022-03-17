package com.anseolab.data.repositories

import com.anseolab.data.datasources.local.AddressLocalDataSource
import com.anseolab.data.datasources.remote.KakaoRemoteDataSource
import com.anseolab.data.mapper.KakaoStoreMapper
import com.anseolab.domain.model.KakaoStore
import com.anseolab.domain.repositories.KakaoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource,
    private val addressLocalDataSource: AddressLocalDataSource
) : KakaoRepository {
    override fun search(
        query: String?,
        x: Double,
        y: Double,
        type: String
    ): Single<List<KakaoStore>> {
        return kakaoRemoteDataSource.search(query, x, y)
            .doOnSuccess { if (type == "search" && query != null) addressLocalDataSource.set(query) }
            .map(KakaoStoreMapper::mapToDomain)
    }

    override fun fetchAddresses(): Flowable<List<String>> {
        return addressLocalDataSource.getAll()
    }

    override fun remove(query: String): Completable {
        return Completable.create { emitter ->
            try {
                addressLocalDataSource.remove(query)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun clear(): Completable {
        return Completable.create { emitter ->
            try {
                addressLocalDataSource.clear()
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
}