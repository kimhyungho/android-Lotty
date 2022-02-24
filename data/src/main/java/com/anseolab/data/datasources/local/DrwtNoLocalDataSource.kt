package com.anseolab.data.datasources.local

import com.anseolab.data.model.DrwtNoData
import io.reactivex.rxjava3.core.Flowable

interface DrwtNoLocalDataSource: LocalDataSource<Int, DrwtNoData> {
    override fun getAll(): Flowable<List<DrwtNoData>>

    override fun set(value: List<DrwtNoData>)

    override fun set(value: DrwtNoData)

    override fun remove(key: Int)

    override fun clear()
}