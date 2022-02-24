package com.anseolab.local.di

import android.content.Context
import com.anseolab.local.databases.DrwtNoDataBase
import com.anseolab.local.databases.LotteryDataBase
import com.anseolab.local.di.qualifiers.LocalGsonQualifier
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    @LocalGsonQualifier
    fun provideJson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideOrganizationDatabase(
        @ApplicationContext
        applicationContext: Context,
        @LocalGsonQualifier
        gson: Gson
    ): LotteryDataBase = LotteryDataBase.getInstance(applicationContext, gson)

    @Singleton
    @Provides
    fun provideDrwtNoDatabase(
        @ApplicationContext
        applicationContext: Context,
        @LocalGsonQualifier
        gson: Gson
    ): DrwtNoDataBase = DrwtNoDataBase.getInstance(applicationContext, gson)

}