package com.anseolab.data.di

import com.anseolab.data.repositories.DhLotteryRepositoryImpl
import com.anseolab.domain.repositories.DhLotteryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindDhLotteryRepository(
        repository: DhLotteryRepositoryImpl
    ): DhLotteryRepository
}