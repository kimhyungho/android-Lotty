package com.anseolab.domain.interactors

import com.anseolab.domain.providers.SchedulerProvider
import io.reactivex.rxjava3.core.Flowable

abstract class FlowableUseCase<Params: Any, T: Any>(
    private val schedulerProvider: SchedulerProvider
) {
    protected open val workerScheduler = schedulerProvider.io

    protected abstract fun build(params: Params? = null): Flowable<T>

    fun execute(params: Params? = null) = Flowable.defer {
        this.build(params)
            .subscribeOn(workerScheduler)
            .observeOn(schedulerProvider.ui)
//            .onErrorResumeNext { throwable: Throwable ->
//                Single.error(FooiyException(throwable.message!!, throwable))
//            }
    }
}