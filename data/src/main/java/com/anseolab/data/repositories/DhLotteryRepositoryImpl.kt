package com.anseolab.data.repositories

import com.anseolab.data.datasources.DhLotteryRemoteDataSource
import com.anseolab.data.mapper.LotteryMapper
import com.anseolab.domain.model.Lottery
import com.anseolab.domain.repositories.DhLotteryRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DhLotteryRepositoryImpl @Inject constructor(
    private val dhLotteryRemoteDataSource: DhLotteryRemoteDataSource
): DhLotteryRepository {
    override fun fetchLotteryNumber(drwNo: Long): Single<Lottery> {
        return dhLotteryRemoteDataSource.fetchLotteryNumber(drwNo)
            .map(LotteryMapper::mapToDomain)
    }
}