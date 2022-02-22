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
    override fun search(query: String, x: Double, y: Double): Single<List<KakaoStoreData>> {
        return kakaoApi.getSearch(query, x, y, 15)
            .map(GetKakaoSearchMapper::mapToData)
    }
}