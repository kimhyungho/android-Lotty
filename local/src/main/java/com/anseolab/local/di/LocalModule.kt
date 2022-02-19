package com.anseolab.local.di

import com.anseolab.data.datasources.local.LotteryLocalDataSource
import com.anseolab.local.datasources.LotteryLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalModule {

    @Singleton
    @Binds
    fun bindLotteryLocalDatasource(
        lotteryLocalDataSource: LotteryLocalDataSourceImpl
    ): LotteryLocalDataSource
}