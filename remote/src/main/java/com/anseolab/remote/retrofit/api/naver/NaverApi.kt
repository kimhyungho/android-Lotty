package com.anseolab.remote.retrofit.api.naver

import com.anseolab.remote.model.response.naver.GetSearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {
    @GET("/v5/api/search")
    fun getSearch(
        @Query("query")
        query: String,
        @Query("searchCoord")
        searchCoord: String,
        @Query("displayCount")
        displayCount: Int
    ): Single<GetSearchResponse>
}