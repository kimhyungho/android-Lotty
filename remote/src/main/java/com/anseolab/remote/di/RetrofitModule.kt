package com.anseolab.remote.di

import com.anseolab.remote.BuildConfig
import com.anseolab.remote.di.qualifiers.DhLotteryQualifier
import com.anseolab.remote.di.qualifiers.KakaoQualifier
import com.anseolab.remote.di.qualifiers.NaverQualifier
import com.anseolab.remote.di.qualifiers.RemoteGsonQualifier
import com.anseolab.remote.retrofit.api.dhlottery.DhLotteryApi
import com.anseolab.remote.retrofit.api.kakao.KakaoApi
import com.anseolab.remote.retrofit.api.naver.NaverApi
import com.anseolab.remote.retrofit.interceptors.AuthInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    @RemoteGsonQualifier
    fun provideJson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @DhLotteryQualifier
    fun provideDhLotteryRetrofit(
        @RemoteGsonQualifier
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.DH_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    @NaverQualifier
    fun provideNaverRetrofit(
        @RemoteGsonQualifier
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NAVER_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideNaverApi(
        @NaverQualifier
        retrofit: Retrofit
    ): NaverApi = retrofit.create(NaverApi::class.java)

    @Singleton
    @Provides
    fun provideDhLotteryApi(
        @DhLotteryQualifier
        retrofit: Retrofit
    ): DhLotteryApi = retrofit.create(DhLotteryApi::class.java)

    @Singleton
    @Provides
    @KakaoQualifier
    fun provideKakaoRetrofit(
        @RemoteGsonQualifier
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.KAKAO_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideKakaoApi(
        @KakaoQualifier
        retrofit: Retrofit
    ): KakaoApi = retrofit.create(KakaoApi::class.java)


}