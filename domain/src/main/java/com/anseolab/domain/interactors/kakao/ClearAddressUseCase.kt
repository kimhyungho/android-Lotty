package com.anseolab.domain.interactors.kakao

import com.anseolab.domain.interactors.CompletableUseCase
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.KakaoService
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearAddressUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val kakaoService: KakaoService
): CompletableUseCase<Unit>(
    schedulerProvider
) {
    override fun build(params: Unit?): Completable {
        return kakaoService.clear()
    }
}