package com.flink.demo.model.interceptors

import com.flink.demo.viewmodel.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = AppPreferences.AUTH_TOKEN
        request = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .build()
        return chain.proceed(request)
    }

}