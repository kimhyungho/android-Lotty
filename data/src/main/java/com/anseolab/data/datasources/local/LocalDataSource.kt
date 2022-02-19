package com.anseolab.data.datasources.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface LocalDataSource<in Key : Any, T> {
    fun getAll(): Flowable<List<T>>

    fun set(key: Key, value: T): Completable

    fun remove(key: Key): Completable

    fun clear(): Completable

//    fun update(models: List<T>): Completable
}