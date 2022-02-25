package com.anseolab.domain.interactors.drwtno

import com.anseolab.domain.interactors.FlowableUseCase
import com.anseolab.domain.model.DrwtNo
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.DrwtNoService
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchDrwtNoUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val drwtNoService: DrwtNoService
): FlowableUseCase<Unit, List<DrwtNo>>(
    schedulerProvider
) {
    override fun build(params: Unit?): Flowable<List<DrwtNo>> {
        return drwtNoService.getAll()
    }
}