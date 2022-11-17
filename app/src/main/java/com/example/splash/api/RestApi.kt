package com.example.splash.api

import com.example.splash.api.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("v1/auth/login")
    fun loginPost(@Body data: LoginPost): Call<ApiResponse<LoginRes>>

    @Headers("Content-Type: application/json")
    @POST("v1/auth/register")
    fun registerPost(@Body data: RegisterPost): Call<ApiResponse<RegisterRes>>
}