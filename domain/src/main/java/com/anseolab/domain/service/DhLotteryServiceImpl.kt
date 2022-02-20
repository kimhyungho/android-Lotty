package com.anseolab.domain.service

import com.anseolab.domain.model.Lottery
import com.anseolab.domain.repositories.DhLotteryRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DhLotteryServiceImpl @Inject constructor(
    private val dhLotteryRepository: DhLotteryRepository
): DhLotteryService {
    override fun searchLottery(drwNo: Long): Single<Lottery> {
        return dhLotteryRepository.searchLotteryNumber(drwNo)
    }

    override fun fetchRecentLotteries(): Flowable<List<Lottery>> {
        return dhLotteryRepository.fetchRecentLotteries()
    }

    override fun fetchLotteryNumber(drwNo: Long): Single<Lottery> {
        return dhLotteryRepository.fetchLotteryNumber(drwNo)
    }

    override fun deleteLottery(drwNo: Long): Completable {
        return dhLotteryRepository.deleteLottery(drwNo)
    }

    override fun clearLotteries(): Completable {
        return dhLotteryRepository.clearLotteries()
    }
}