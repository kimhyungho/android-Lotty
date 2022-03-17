package com.anseolab.domain.service

import com.anseolab.domain.model.KakaoStore
import com.anseolab.domain.repositories.KakaoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class KakaoServiceImpl @Inject constructor(
    private val kakaoRepository: KakaoRepository
) : KakaoService {
    override fun search(
        query: String?,
        x: Double,
        y: Double,
        type: String
    ): Single<List<KakaoStore>> {
        return kakaoRepository.search(query, x, y, type)
    }

    override fun fetchAddress(): Flowable<List<String>> {
        return kakaoRepository.fetchAddresses()
    }

    override fun clear(): Completable {
        return kakaoRepository.clear()
    }

    override fun remove(address: String): Completable {
        return kakaoRepository.remove(address)
    }
}