package com.anseolab.remote.di

import com.anseolab.data.datasources.DhLotteryRemoteDataSource
import com.anseolab.remote.datasources.DhLotteryRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {
    @Singleton
    @Binds
    fun bindDhLotteryRemoteDataSource(
        dataSource: DhLotteryRemoteDataSourceImpl
    ): DhLotteryRemoteDataSource
}