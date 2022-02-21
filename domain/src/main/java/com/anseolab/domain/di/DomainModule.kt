package com.anseolab.domain.di

import com.anseolab.domain.service.DhLotteryService
import com.anseolab.domain.service.DhLotteryServiceImpl
import com.anseolab.domain.service.NaverService
import com.anseolab.domain.service.NaverServiceImpl
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
}