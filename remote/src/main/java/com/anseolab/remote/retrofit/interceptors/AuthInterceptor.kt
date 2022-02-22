package com.anseolab.remote.retrofit.interceptors

import android.util.Log
import com.anseolab.remote.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return this.doRequest(chain)
    }

    private fun doRequest(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (isKakaoBaseUrl(request)) {
            return chain.proceed(
                request
                    .newBuilder()
                    .addHeader(KAKAO_HEADER_AUTHORIZATION, "$BASE_AUTHORIZATION_PREFIX ${BuildConfig.KAKAO_REST_API_KEY}")
                    .build()
            )
        }

        return chain.proceed(request)
    }

    private fun isKakaoBaseUrl(request: Request): Boolean {
        val urlString = request.url.toString()
        if (urlString.startsWith(BuildConfig.KAKAO_BASE_URL)) {
            return true
        }
        return false
    }

    companion object {
        private const val KAKAO_HEADER_AUTHORIZATION = "Authorization"
        private const val BASE_AUTHORIZATION_PREFIX = "KakaoAK"
    }
}