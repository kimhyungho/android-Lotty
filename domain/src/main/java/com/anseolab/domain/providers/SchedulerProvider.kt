package com.anseolab.domain.providers

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerProvider {
    val io: Scheduler

    val ui: Scheduler

    val computation: Scheduler
}