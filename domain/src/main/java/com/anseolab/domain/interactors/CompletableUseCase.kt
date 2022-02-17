package com.anseolab.domain.interactors

import com.anseolab.domain.providers.SchedulerProvider
import io.reactivex.rxjava3.core.Completable
import javax.inject.Singleton

@Singleton
abstract class CompletableUseCase<Params>(
    private val schedulerProvider: SchedulerProvider
) {
    protected open val workerScheduler = schedulerProvider.io

    protected abstract fun build(params: Params? = null): Completable

    fun execute(params: Params? = null) = Completable.defer {
        this.build(params)
            .subscribeOn(workerScheduler)
            .observeOn(schedulerProvider.ui)
    }

}