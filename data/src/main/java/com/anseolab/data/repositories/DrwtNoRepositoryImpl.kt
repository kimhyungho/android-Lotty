package com.anseolab.data.repositories

import com.anseolab.data.datasources.local.DrwtNoLocalDataSource
import com.anseolab.data.mapper.DrwtNoMapper
import com.anseolab.data.model.DrwtNoData
import com.anseolab.domain.model.DrwtNo
import com.anseolab.domain.repositories.DrwtNoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class DrwtNoRepositoryImpl @Inject constructor(
    private val drwtNoLocalDataSource: DrwtNoLocalDataSource
) : DrwtNoRepository {
    override fun set(
        drwtNo1: Int,
        drwtNo2: Int,
        drwtNo3: Int,
        drwtNo4: Int,
        drwtNo5: Int,
        drwtNo6: Int,
        bnusNo: Int
    ): Completable {
        val data = DrwtNoData(
            drwtNo1 = drwtNo1,
            drwtNo2 = drwtNo2,
            drwtNo3 = drwtNo3,
            drwtNo4 = drwtNo4,
            drwtNo5 = drwtNo5,
            drwtNo6 = drwtNo6,
            bnusNo = bnusNo
        )
        return Completable.create { emitter ->
            try {
                drwtNoLocalDataSource.set(data)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun remove(id: Int): Completable {
        return Completable.create { emitter ->
            try {
                drwtNoLocalDataSource.remove(id)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun getAll(): Flowable<List<DrwtNo>> {
        return drwtNoLocalDataSource.getAll()
            .map(DrwtNoMapper::mapToDomain)
    }

    override fun clear(): Completable {
        return Completable.create { emitter ->
            try {
                drwtNoLocalDataSource.clear()
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }
}