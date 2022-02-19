package com.anseolab.domain.interactors.dhlottery

import com.anseolab.domain.interactors.FlowableUseCase
import com.anseolab.domain.model.Lottery
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.DhLotteryService
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchRecentLotteriesUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val dhLotteryService: DhLotteryService
): FlowableUseCase<Any, List<Lottery>>(
    schedulerProvider
) {

    override fun build(params: Any?): Flowable<List<Lottery>> {
        TODO("Not yet implemented")
    }
}