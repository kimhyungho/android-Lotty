package com.anseolab.domain.service

import com.anseolab.domain.model.DrwtNo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface DrwtNoService {
    fun getAll(): Flowable<List<DrwtNo>>

    fun set(
        drwtNo1: Int,
        drwtNo2: Int,
        drwtNo3: Int,
        drwtNo4: Int,
        drwtNo5: Int,
        drwtNo6: Int,
        bnusNo: Int
    ): Completable
}