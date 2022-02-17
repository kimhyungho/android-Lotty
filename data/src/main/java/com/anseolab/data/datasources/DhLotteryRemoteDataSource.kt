package com.anseolab.data.datasources

import com.anseolab.data.model.LotteryData
import io.reactivex.rxjava3.core.Single

interface DhLotteryRemoteDataSource {
    fun fetchLotteryNumber(drwNo: Long): Single<LotteryData>
}