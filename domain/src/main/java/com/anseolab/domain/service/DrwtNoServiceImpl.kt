package com.anseolab.domain.service

import com.anseolab.domain.model.DrwtNo
import com.anseolab.domain.repositories.DrwtNoRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class DrwtNoServiceImpl @Inject constructor(
    private val drwtNoRepository: DrwtNoRepository
): DrwtNoService {
    override fun getAll(): Flowable<List<DrwtNo>> {
        return drwtNoRepository.getAll()
    }

    override fun clear(): Completable {
        return drwtNoRepository.clear()
    }

    override fun remove(id: Int): Completable {
        return drwtNoRepository.remove(id)
    }

    override fun set(
        drwtNo1: Int,
        drwtNo2: Int,
        drwtNo3: Int,
        drwtNo4: Int,
        drwtNo5: Int,
        drwtNo6: Int,
        bnusNo: Int
    ): Completable {
        return drwtNoRepository.set(drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6, bnusNo)
    }
}