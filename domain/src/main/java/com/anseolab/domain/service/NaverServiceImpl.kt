package com.anseolab.domain.service

import com.anseolab.domain.model.Store
import com.anseolab.domain.repositories.NaverRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NaverServiceImpl @Inject constructor(
    private val naverRepository: NaverRepository
) : NaverService {
    override fun search(query: String, x: Double, y: Double): Single<List<Store>> {
        return naverRepository.search(query, x, y)
    }
}