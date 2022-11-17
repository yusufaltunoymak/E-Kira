package com.example.splash.api

import com.example.splash.api.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
    fun loginPost(data: LoginPost, onResult: (ApiResponse<LoginRes>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.loginPost(data).enqueue(
            object : Callback<ApiResponse<LoginRes>> {
                override fun onFailure(call: Call<ApiResponse<LoginRes>>, t: Throwable) {
                    println(t.message)
                    onResult(null)
                }
                override fun onResponse( call: Call<ApiResponse<LoginRes>>, response: Response<ApiResponse<LoginRes>>) {
                    println(response.body().toString())
                    val loginResult = response.body()
                    println(loginResult)
                    onResult(loginResult)
                }
            }
        )
    }

    fun registerPost(data: RegisterPost, onResult: (ApiResponse<RegisterRes>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.registerPost(data).enqueue(
            object : Callback<ApiResponse<RegisterRes>> {
                override fun onFailure(call: Call<ApiResponse<RegisterRes>>, t: Throwable) {
                    println(t.message)
                    onResult(null)
                }
                override fun onResponse( call: Call<ApiResponse<RegisterRes>>, response: Response<ApiResponse<RegisterRes>>) {
                    println(response.body().toString())
                    val loginResult = response.body()
                    println(loginResult)
                    onResult(loginResult)
                }
            }
        )
    }
}