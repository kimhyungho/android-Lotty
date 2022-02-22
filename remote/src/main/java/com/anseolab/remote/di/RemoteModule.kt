package com.anseolab.remote.di

import com.anseolab.data.datasources.DhLotteryRemoteDataSource
import com.anseolab.data.datasources.remote.KakaoRemoteDataSource
import com.anseolab.data.datasources.remote.NaverRemoteDataSource
import com.anseolab.remote.datasources.DhLotteryRemoteDataSourceImpl
import com.anseolab.remote.datasources.KakaoRemoteDataSourceImpl
import com.anseolab.remote.datasources.NaverRemoteDataSourceImpl
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

    @Singleton
    @Binds
    fun bindNaverRemoteDataSource(
        dataSource: NaverRemoteDataSourceImpl
    ): NaverRemoteDataSource

    @Singleton
    @Binds
    fun bindKakaoRemoteDataSource(
        dataSource: KakaoRemoteDataSourceImpl
    ): KakaoRemoteDataSource
}