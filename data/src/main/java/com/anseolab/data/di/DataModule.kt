package com.anseolab.data.di

import com.anseolab.data.repositories.DhLotteryRepositoryImpl
import com.anseolab.data.repositories.KakaoRepositoryImpl
import com.anseolab.data.repositories.NaverRepositoryImpl
import com.anseolab.domain.repositories.DhLotteryRepository
import com.anseolab.domain.repositories.KakaoRepository
import com.anseolab.domain.repositories.NaverRepository
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

    @Singleton
    @Binds
    fun bindNaverRepository(
        repository: NaverRepositoryImpl
    ): NaverRepository

    @Singleton
    @Binds
    fun bindKakaoRepository(
        repository: KakaoRepositoryImpl
    ): KakaoRepository


}