package com.anseolab.data.cache

import androidx.annotation.WorkerThread
import com.anseolab.data.datasources.local.LocalDataSource
import io.reactivex.rxjava3.core.Single

abstract class CacheManager<Key: Any, Data>(
    private val local: LocalDataSource<Key, Data>
) {






}