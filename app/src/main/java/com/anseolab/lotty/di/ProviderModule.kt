package com.anseolab.lotty.di

import com.anseolab.domain.providers.SchedulerProvider
import com.anseolab.lotty.providers.permissions.PermissionProvider
import com.anseolab.lotty.providers.permissions.PermissionProviderImpl
import com.anseolab.lotty.providers.resource.ResourceProvider
import com.anseolab.lotty.providers.resource.ResourceProviderImpl
import com.anseolab.lotty.providers.scheduler.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProviderModule {

    @Singleton
    @Binds
    fun bindSchedulerProvider(
        provider: SchedulerProviderImpl
    ): SchedulerProvider

    @Singleton
    @Binds
    fun bindPermissionProvider(
        provider: PermissionProviderImpl
    ): PermissionProvider

//    @Singleton
//    @Binds
//    fun bindSessionPreferenceProvider(
//        provider: SessionPreferenceProviderImpl
//    ): SessionPreferenceProvider
//
//    @Singleton
//    @Binds
//    fun bindPermissionProvider(
//        provider: PermissionProviderImpl
//    ): PermissionProvider
//
//    @Singleton
//    @Binds
//    fun bindLocationProvider(
//        provider: LocationProviderImpl
//    ): LocationProvider

    @Singleton
    @Binds
    fun bindResourceProvider(
        provider: ResourceProviderImpl
    ): ResourceProvider


}