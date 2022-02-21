package com.anseolab.domain.repositories

import com.anseolab.domain.model.Store
import io.reactivex.rxjava3.core.Single

interface NaverRepository {
    fun search(query: String, x: Double, y: Double): Single<List<Store>>
}