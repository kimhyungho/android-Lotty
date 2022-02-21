package com.anseolab.domain.service

import com.anseolab.domain.model.Store
import io.reactivex.rxjava3.core.Single

interface NaverService {
    fun search(query: String, x: Double, y: Double): Single<List<Store>>
}