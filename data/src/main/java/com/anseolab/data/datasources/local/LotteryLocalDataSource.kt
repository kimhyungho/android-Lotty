package com.anseolab.data.datasources.local

import com.anseolab.data.model.LotteryData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface LotteryLocalDataSource: LocalDataSource<Long, LotteryData> {
    override fun getAll(): Flowable<List<LotteryData>>

    override fun set(key: Long, value: LotteryData) : Completable

    override fun remove(key: Long) : Completable

    override fun clear() : Completable
}