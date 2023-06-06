package com.example.splash.api

import com.example.splash.BuildConfig
import okhttp3.*
import java.io.IOException

class RestApiInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentUserAgent = System.getProperty("http.agent")
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("appid", "e-kira")
            .addHeader("appversion", BuildConfig.VERSION_NAME)
            .addHeader("deviceplatform", "android")
            .removeHeader("User-Agent")
            .addHeader(
                "User-Agent",
                currentUserAgent + " e-kira/" + BuildConfig.VERSION_NAME
            )
            .build()
        val response = chain.proceed(request)
        println(response.body().toString())
        if(response.code() in 502..504) {
            throw IOException("Server error code: " + response.code().toString() + " with error message: " + response.message().toString());
        }
        return response
    }
}