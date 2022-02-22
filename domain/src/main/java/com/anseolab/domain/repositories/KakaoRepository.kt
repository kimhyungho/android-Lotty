package com.anseolab.domain.repositories

import com.anseolab.domain.model.KakaoStore
import io.reactivex.rxjava3.core.Single

interface KakaoRepository {
    fun search(query: String, x: Double, y: Double): Single<List<KakaoStore>>
}