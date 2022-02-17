package com.anseolab.remote.datasources

import com.anseolab.data.datasources.DhLotteryRemoteDataSource
import com.anseolab.data.model.LotteryData
import com.anseolab.remote.mapper.GetCommonDoMapper
import com.anseolab.remote.model.response.dhlottery.GetCommonDoResponse
import com.anseolab.remote.retrofit.api.dhlottery.DhLotteryApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DhLotteryRemoteDataSourceImpl @Inject constructor(
    private val dhLotteryApi: DhLotteryApi
): DhLotteryRemoteDataSource {
    override fun fetchLotteryNumber(drwNo: Long): Single<LotteryData> {
        return dhLotteryApi.getCommonDo(drwNo = drwNo)
            .map(GetCommonDoMapper::mapToData)
    }
}