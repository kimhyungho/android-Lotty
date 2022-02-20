package com.anseolab.domain.interactors.dhlottery

import com.anseolab.domain.interactors.CompletableUseCase
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.DhLotteryService
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteLotteryUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val dhLotteryService: DhLotteryService
): CompletableUseCase<Long>(
    schedulerProvider
) {
    override fun build(params: Long?): Completable {
        val drwNo = params ?: throw IllegalArgumentException("params can not be null")
        return dhLotteryService.deleteLottery(drwNo)
    }
}