package com.anseolab.domain.interactors.drwtno

import com.anseolab.domain.interactors.CompletableUseCase
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.DrwtNoService
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetDrwtNoUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val drwtNoService: DrwtNoService
) : CompletableUseCase<SetDrwtNoUseCase.Params>(
    schedulerProvider
) {
    override fun build(params: Params?): Completable {
        if (params == null) throw  IllegalArgumentException("params can not be null")
        val (drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6, bnusNo) = params
        return drwtNoService.set(drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6, bnusNo)
    }

    data class Params(
        val drwtNo1: Int,
        val drwtNo2: Int,
        val drwtNo3: Int,
        val drwtNo4: Int,
        val drwtNo5: Int,
        val drwtNo6: Int,
        val bnusNo: Int
    )
}