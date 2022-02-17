package com.anseolab.remote.retrofit.api.dhlottery

import com.anseolab.remote.model.response.dhlottery.GetCommonDoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DhLotteryApi {
    @GET("/common.do")
    fun getCommonDo(
        @Query("method")
        method : String = "getLottoNumber",
        @Query("drwNo")
        drwNo: Long
    ): Single<GetCommonDoResponse>
}