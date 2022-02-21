package com.anseolab.domain.interactors.naver

import com.anseolab.domain.interactors.SingleUseCase
import com.anseolab.domain.model.Store
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.NaverService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val naverService: NaverService
): SingleUseCase<SearchUseCase.Params, List<Store>>(
    schedulerProvider
) {
    override fun build(params: Params?): Single<List<Store>> {
        if(params == null) throw IllegalArgumentException("params can not be null")
        val (query, x, y) = params
        return naverService.search(query, x, y)
    }

    data class Params(
        val query: String,
        val x: Double,
        val y: Double
    )
}