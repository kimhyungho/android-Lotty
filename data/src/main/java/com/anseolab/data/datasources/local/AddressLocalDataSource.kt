package com.anseolab.data.datasources.local

import io.reactivex.rxjava3.core.Flowable

interface AddressLocalDataSource: LocalDataSource<String, String> {
    override fun getAll(): Flowable<List<String>>

    override fun set(value: List<String>)

    override fun set(value: String)

    override fun remove(key: String)

    override fun clear()
}