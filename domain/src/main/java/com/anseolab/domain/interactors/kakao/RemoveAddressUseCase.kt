package com.anseolab.domain.interactors.kakao

import com.anseolab.domain.interactors.CompletableUseCase
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.KakaoService
import io.reactivex.rxjava3.core.Completable
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveAddressUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val kakaoService: KakaoService
) : CompletableUseCase<String>(
    schedulerProvider
) {
    override fun build(params: String?): Completable {
        val address = params ?: throw IllegalStateException("address can not be null")
        return kakaoService.remove(address)
    }
}