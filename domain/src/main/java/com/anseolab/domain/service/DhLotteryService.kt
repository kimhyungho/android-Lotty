package com.anseolab.domain.service

import com.anseolab.domain.model.Lottery
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface DhLotteryService {
    fun searchLottery(drwNo: Long): Single<Lottery>

    fun fetchRecentLotteries(): Flowable<List<Lottery>>

    fun fetchLotteryNumber(drwNo: Long): Single<Lottery>

    fun deleteLottery(drwNo: Long): Completable

    fun clearLotteries(): Completable
}