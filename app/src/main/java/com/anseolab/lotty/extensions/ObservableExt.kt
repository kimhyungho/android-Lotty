package com.anseolab.lotty.extensions

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

fun <T : Any> Observable<T>.throttle(): Observable<T> {
    return this.throttleFirst(
        1000,
        TimeUnit.MILLISECONDS,
        AndroidSchedulers.mainThread()
    )
}