package com.anseolab.domain.interactors.dhlottery

import com.anseolab.domain.interactors.SingleUseCase
import com.anseolab.domain.model.Lottery
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.DhLotteryService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchLotteryUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val dhLotteryService: DhLotteryService
): SingleUseCase<Long, Lottery>(
    schedulerProvider
) {
    override fun build(params: Long?): Single<Lottery> {
        if(params == null) throw IllegalArgumentException("params can not be null")
        return dhLotteryService.searchLottery(params)
    }
}