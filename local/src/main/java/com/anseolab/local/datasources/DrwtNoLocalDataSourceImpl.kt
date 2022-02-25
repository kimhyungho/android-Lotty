package com.anseolab.local.datasources

import com.anseolab.data.datasources.local.DrwtNoLocalDataSource
import com.anseolab.data.model.DrwtNoData
import com.anseolab.local.databases.DrwtNoDataBase
import com.anseolab.local.mapper.FetchDrwtNoMapper
import com.anseolab.local.model.entity.DrwtNoEntity
import io.reactivex.rxjava3.core.Flowable
import java.lang.UnsupportedOperationException
import java.util.*
import javax.inject.Inject

class DrwtNoLocalDataSourceImpl @Inject constructor(
    private val drwtNoDataBase: DrwtNoDataBase
) : DrwtNoLocalDataSource {
    override fun getAll(): Flowable<List<DrwtNoData>> {
        return drwtNoDataBase.drwtNoDao.getAll()
            .map(FetchDrwtNoMapper::mapToData)
    }

    override fun set(value: List<DrwtNoData>) {
        throw UnsupportedOperationException()
    }

    override fun set(value: DrwtNoData) {
        val entity = DrwtNoEntity(
            drwtNo1 = value.drwtNo1,
            drwtNo2 = value.drwtNo2,
            drwtNo3 = value.drwtNo3,
            drwtNo4 = value.drwtNo4,
            drwtNo5 = value.drwtNo5,
            drwtNo6 = value.drwtNo6,
            bnusNo = value.bnusNo,
            createdAt = Date().time
        )
        drwtNoDataBase.drwtNoDao.set(entity)
    }

    override fun remove(key: Int) {
        drwtNoDataBase.drwtNoDao.remove(key)
    }

    override fun clear() {
        drwtNoDataBase.drwtNoDao.clear()
    }
}