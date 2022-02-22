package com.anseolab.domain.service

import com.anseolab.domain.model.KakaoStore
import com.anseolab.domain.repositories.KakaoRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class KakaoServiceImpl @Inject constructor(
    private val kakaoRepository: KakaoRepository
): KakaoService {
    override fun search(query: String, x: Double, y: Double): Single<List<KakaoStore>> {
        return kakaoRepository.search(query, x, y)
    }
}