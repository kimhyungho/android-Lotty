package com.anseolab.domain.interactors.drwtno

import com.anseolab.domain.interactors.CompletableUseCase
import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.domain.service.DrwtNoService
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearDrwtNoUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,

    private val drwtNoService: DrwtNoService
): CompletableUseCase<Unit>(schedulerProvider) {
    override fun build(params: Unit?): Completable {
        return drwtNoService.clear()
    }
}