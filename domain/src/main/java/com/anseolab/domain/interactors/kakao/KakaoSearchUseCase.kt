package com.anseolab.domain.interactors.kakao

import com.anseolab.domain.interactors.SingleUseCase
import com.anseolab.domain.model.KakaoStore
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.KakaoService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoSearchUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val kakaoService: KakaoService
): SingleUseCase<KakaoSearchUseCase.Params, List<KakaoStore>>(
    schedulerProvider
) {
    override fun build(params: Params?): Single<List<KakaoStore>> {
        if(params == null) throw IllegalArgumentException("params can not be null")
        val (query, x, y, type) = params
        return kakaoService.search(query, x, y, type)
    }

    data class Params(
        val query: String?,
        val x: Double,
        val y: Double,
        val type: String
    )
}