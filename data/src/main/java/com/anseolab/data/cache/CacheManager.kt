package com.anseolab.data.cache

import android.util.Log
import androidx.annotation.WorkerThread
import com.anseolab.data.datasources.local.LocalDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import java.util.*

abstract class CacheManager<Key : Any, Data>(
    private val local: LocalDataSource<Key, Data>
) {
    open fun fetch(): Flowable<List<Data>> {
        val result = local.getAll()
        return result
            .firstOrError()
            .flatMapPublisher { models ->
                Flowable.defer {
                    if (models.isEmpty()) {
                        this.fetchFromRemoteAndCache()
                            .andThen(result)
                    } else {
                        Flowable.concat(
                            Flowable.just(models),
                            result.skip(1)
                        )
                    }
                }
            }
    }

    open fun fetch(key: Key): Flowable<List<Data>> {
        val result = local.getAll()
        return result
            .firstOrError()
            .flatMapPublisher { models ->
                Flowable.defer {
                    if (models.isEmpty()) {
                        this.fetchFromRemoteAndCache(key)
                            .andThen(result)
                    } else {
                        Flowable.concat(
                            Flowable.just(models),
                            result.skip(1)
                        )
                    }
                }
            }
    }

    open fun refresh(key: Key): Completable {
        return this.fetchFromRemoteAndCache(key, force = false)
    }

    open fun refresh(): Completable {
        return this.fetchFromRemoteAndCache(force = true)
    }

    private fun fetchFromRemoteAndCache(force: Boolean = false): Completable {
        return Completable.defer {
            this.fetchFromRemote()
                .flatMapCompletable { models ->
                    this.cache(models)
                }
        }
    }

    private fun fetchFromRemoteAndCache(key: Key, force: Boolean = false): Completable {
        return Completable.defer {
            this.fetchFromRemoteByKey(key)
                .flatMapCompletable { model ->
                    this.cache(model)
                }
        }
    }

    @WorkerThread
    private fun cache(models: List<Data>, withClear: Boolean = false): Completable {
        return Completable.fromCallable {
            if(withClear) local.clear()
            local.set(models)
        }
    }

    @WorkerThread
    private fun cache(model: Data): Completable {
        return Completable.fromCallable {
            local.set(model)
        }
    }

    abstract fun fetchFromRemote(): Single<List<Data>>

    abstract fun fetchFromRemoteByKey(key: Key): Single<Data>

}