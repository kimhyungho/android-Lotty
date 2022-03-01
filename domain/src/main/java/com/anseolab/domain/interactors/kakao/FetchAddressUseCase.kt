package com.anseolab.domain.interactors.kakao

import com.anseolab.domain.interactors.FlowableUseCase
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.KakaoService
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchAddressUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val kakaoService: KakaoService
): FlowableUseCase<Unit, List<String>>(
    schedulerProvider
) {

    override fun build(params: Unit?): Flowable<List<String>> {
        return kakaoService.fetchAddress()
    }
}