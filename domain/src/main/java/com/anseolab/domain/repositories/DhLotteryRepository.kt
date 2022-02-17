package com.anseolab.domain.repositories

import com.anseolab.domain.model.Lottery
import io.reactivex.rxjava3.core.Single

interface DhLotteryRepository {
    fun fetchLotteryNumber(drwNo: Long): Single<Lottery>
}