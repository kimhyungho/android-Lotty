package com.anseolab.data.datasources.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface LocalDataSource<in Key : Any, T> {
    fun getAll(): Flowable<List<T>>

    fun set(value: List<T>)

    fun set(value: T)

    fun remove(key: Key)

    fun clear()

//    fun update(models: List<T>): Completable

}