package com.anseolab.domain.interactors.dhlottery

import com.anseolab.domain.interactors.CompletableUseCase
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.DhLotteryService
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearLotteriesUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val dhLotteryService: DhLotteryService
) : CompletableUseCase<Unit>(
    schedulerProvider
) {
    override fun build(params: Unit?): Completable {
        return dhLotteryService.clearLotteries()
    }
}