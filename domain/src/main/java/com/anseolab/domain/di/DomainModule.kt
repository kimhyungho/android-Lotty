package com.anseolab.domain.di

import com.anseolab.domain.service.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Singleton
    @Binds
    fun bindAccountsService(
        service: DhLotteryServiceImpl
    ): DhLotteryService

    @Singleton
    @Binds
    fun bindNaverService(
        service: NaverServiceImpl
    ): NaverService

    @Singleton
    @Binds
    fun bindKakaoService(
        service: KakaoServiceImpl
    ): KakaoService
}