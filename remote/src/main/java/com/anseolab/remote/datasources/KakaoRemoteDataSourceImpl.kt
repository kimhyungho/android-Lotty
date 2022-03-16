package com.anseolab.remote.datasources

import com.anseolab.data.datasources.remote.KakaoRemoteDataSource
import com.anseolab.data.model.KakaoStoreData
import com.anseolab.remote.mapper.GetKakaoSearchMapper
import com.anseolab.remote.retrofit.api.kakao.KakaoApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class KakaoRemoteDataSourceImpl @Inject constructor(
    private val kakaoApi: KakaoApi
) : KakaoRemoteDataSource {
    override fun search(query: String?, x: Double, y: Double): Single<List<KakaoStoreData>> {
        val searchQuery = if(query == null) "복권 판매점" else "$query 복권 판매점"
        return kakaoApi.getSearch(searchQuery , x, y, 15)
            .map(GetKakaoSearchMapper::mapToData)
    }
}