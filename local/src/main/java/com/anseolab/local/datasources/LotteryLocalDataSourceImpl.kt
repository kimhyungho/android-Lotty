package com.anseolab.local.datasources

import android.util.Log
import com.anseolab.data.datasources.local.LotteryLocalDataSource
import com.anseolab.data.model.LotteryData
import com.anseolab.local.databases.LotteryDataBase
import com.anseolab.local.mapper.FetchLotteryMapper
import com.anseolab.local.mapper.LotteryEntityMapper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class LotteryLocalDataSourceImpl @Inject constructor(
    private val lotteryDataBase: LotteryDataBase
) : LotteryLocalDataSource {
    override fun getAll(): Flowable<List<LotteryData>> {
        return lotteryDataBase.lotteryDao.getAll()
            .map(FetchLotteryMapper::mapToData)
    }

    override fun set(value: List<LotteryData>) {
        throw UnsupportedOperationException()
    }

    override fun set(value: LotteryData) {
        val data = LotteryEntityMapper.mapToData(value)
        lotteryDataBase.lotteryDao.set(data)
    }

    override fun remove(key: Long) {
        lotteryDataBase.lotteryDao.remove(key)
    }

    override fun clear() {
        lotteryDataBase.lotteryDao.clear()
    }
}