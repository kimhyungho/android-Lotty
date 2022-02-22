package com.anseolab.remote.retrofit.api.kakao

import com.anseolab.remote.model.response.kakao.GetKakaoSearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoApi {
    @GET("/v2/local/search/keyword.json")
    fun getSearch(
        @Query("query")
        query: String,
        @Query("x")
        x: Double,
        @Query("y")
        y: Double,
        @Query("size")
        size: Int
    ): Single<GetKakaoSearchResponse>
}