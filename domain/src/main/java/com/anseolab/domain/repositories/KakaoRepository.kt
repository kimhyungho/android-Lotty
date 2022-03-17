package com.anseolab.domain.repositories

import com.anseolab.domain.model.KakaoStore
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface KakaoRepository {
    fun search(query: String?, x: Double, y: Double, type: String): Single<List<KakaoStore>>

    fun fetchAddresses(): Flowable<List<String>>

    fun remove(query: String): Completable

    fun clear(): Completable
}