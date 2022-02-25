package com.anseolab.domain.repositories

import com.anseolab.domain.model.DrwtNo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface DrwtNoRepository {
    fun set(
        drwtNo1: Int,
        drwtNo2: Int,
        drwtNo3: Int,
        drwtNo4: Int,
        drwtNo5: Int,
        drwtNo6: Int,
        bnusNo: Int
    ): Completable

    fun remove(id: Int): Completable

    fun getAll(): Flowable<List<DrwtNo>>

    fun clear(): Completable
}