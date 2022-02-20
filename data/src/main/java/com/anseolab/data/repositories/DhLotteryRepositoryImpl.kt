package com.anseolab.data.repositories

import android.util.Log
import androidx.annotation.WorkerThread
import com.anseolab.data.cache.CacheManager
import com.anseolab.data.datasources.DhLotteryRemoteDataSource
import com.anseolab.data.datasources.local.LotteryLocalDataSource
import com.anseolab.data.mapper.LotteriesMapper
import com.anseolab.data.mapper.LotteryMapper
import com.anseolab.data.model.LotteryData
import com.anseolab.domain.model.Lottery
import com.anseolab.domain.repositories.DhLotteryRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import java.util.*
import javax.inject.Inject

class DhLotteryRepositoryImpl @Inject constructor(
    private val dhLotteryRemoteDataSource: DhLotteryRemoteDataSource,
    private val lotteryLocalDataSource: LotteryLocalDataSource
) : DhLotteryRepository {
//    private val cacheManager = object : CacheManager<Long, LotteryData>(lotteryLocalDataSource) {
//        override fun fetchFromRemote(): Single<List<LotteryData>> {
//            throw UnsupportedOperationException()
//        }
//
//        override fun fetchFromRemoteByKey(key: Long): Single<LotteryData> {
//            return dhLotteryRemoteDataSource.fetchLotteryNumber(drwNo = key)
//        }
//    }

    @WorkerThread
    override fun searchLotteryNumber(drwNo: Long): Single<Lottery> {
        return dhLotteryRemoteDataSource.fetchLotteryNumber(drwNo)
            .doOnSuccess {
                lotteryLocalDataSource.set(it)
            }
            .map(LotteryMapper::mapToDomain)
    }

    override fun fetchLotteryNumber(drwNo: Long): Single<Lottery> {
        return dhLotteryRemoteDataSource.fetchLotteryNumber(drwNo)
            .map(LotteryMapper::mapToDomain)
    }

    override fun fetchRecentLotteries(): Flowable<List<Lottery>> {
        val result = lotteryLocalDataSource.getAll()
        return result
            .firstOrError()
            .flatMapPublisher { models ->
                Flowable.defer {
                    Flowable.concat(
                        Flowable.just(models),
                        result.skip(1)
                    )
                }
            }.map(LotteriesMapper::mapToDomain)
    }

    override fun deleteLottery(drwNo: Long): Completable {
        return Completable.create { emitter ->
            try {
                lotteryLocalDataSource.remove(drwNo)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun clearLotteries(): Completable {
        return Completable.create { emitter ->
            try {
                lotteryLocalDataSource.clear()
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
}