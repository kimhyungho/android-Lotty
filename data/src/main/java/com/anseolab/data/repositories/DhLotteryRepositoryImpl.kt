package com.anseolab.data.repositories

import com.anseolab.data.datasources.DhLotteryRemoteDataSource
import com.anseolab.data.datasources.local.LotteryLocalDataSource
import com.anseolab.data.mapper.LotteriesMapper
import com.anseolab.data.mapper.LotteryMapper
import com.anseolab.domain.model.Lottery
import com.anseolab.domain.repositories.DhLotteryRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DhLotteryRepositoryImpl @Inject constructor(
    private val dhLotteryRemoteDataSource: DhLotteryRemoteDataSource,
    private val lotteryLocalDataSource: LotteryLocalDataSource
) : DhLotteryRepository {
    override fun searchLotteryNumber(drwNo: Long): Single<Lottery> {
        return dhLotteryRemoteDataSource.fetchLotteryNumber(drwNo)
            .doOnSuccess { lottery -> lotteryLocalDataSource.set(drwNo, lottery) }
            .map(LotteryMapper::mapToDomain)
    }

    override fun fetchLotteryNumber(drwNo: Long): Single<Lottery> {
        return dhLotteryRemoteDataSource.fetchLotteryNumber(drwNo)
            .map(LotteryMapper::mapToDomain)
    }

    override fun fetchRecentLotteries(): Flowable<List<Lottery>> {
        return lotteryLocalDataSource.getAll()
            .map(LotteriesMapper::mapToDomain)
    }
}