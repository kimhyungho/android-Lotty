package com.anseolab.domain.service

import com.anseolab.domain.model.KakaoStore
import io.reactivex.rxjava3.core.Single


interface KakaoService {
    fun search(query: String, x: Double, y: Double): Single<List<KakaoStore>>
}