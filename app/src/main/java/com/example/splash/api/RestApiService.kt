package com.example.splash.api

import android.content.Context
import com.example.splash.api.models.*
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    private var mContext: Context

    constructor(context: Context) {
        mContext = context
    }

    fun loginPost(data: LoginPost, onResult: (ApiResponse<LoginRes>?) -> Unit){
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.loginPost(data).enqueue(
            createCallback(onResult)
        )
    }

    fun registerPost(data: RegisterPost, onResult: (ApiResponse<RegisterRes>?) -> Unit){
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.registerPost(data).enqueue(
            createCallback(onResult)
        )
    }

    fun checkAuth(onResult: (ApiResponse<User>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.checkAuth().enqueue(
            createCallback(onResult)
        )
    }

    fun getCities(onResult: (ApiResponse<List<City>>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getCities().enqueue(
            createCallback(onResult)
        )
    }

    fun getTowns(cityId : Int, onResult: (ApiResponse<List<Town>>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getTowns(cityId).enqueue(
            createCallback(onResult)
        )
    }

    fun getDistricts(townId : Int, onResult: (ApiResponse<List<District>>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getDistricts(townId).enqueue(
            createCallback(onResult)
        )
    }

    fun getQuarters(districtId : Int, onResult: (ApiResponse<List<Quarter>>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getQuarters(districtId).enqueue(
            createCallback(onResult)
        )
    }

    fun logoutAuth(onResult: (ApiResponse<String>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.logoutAuth().enqueue(
            createCallback(onResult)
        )
    }

    fun setPhone(data: SetPhonePost, onResult: (ApiResponse<SetPhoneResult>?)-> Unit){
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.setPhone(data).enqueue(
            createCallback(onResult)
        )
    }

    fun verifyPhone(data: VerifyPhonePost, onResult: (ApiResponse<VerifyPhoneResult>?)-> Unit){
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.verifyPhone(data).enqueue(
            createCallback(onResult)
        )
    }

    fun setProfile(data: SetProfile, onResult: (ApiResponse<SetProfile>?) -> Unit){
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.setProfile(data).enqueue(
            createCallback(onResult)
        )
    }

    fun getRentalHouseList(page: Int, limit: Int, sort: String, search: String, onlyFavorite: Boolean, onResult: (ApiResponse<RentalHouseList>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getRentalHouseList(page, limit, sort, search, onlyFavorite).enqueue(
            createCallback(onResult)
        )
    }

    fun favoriteRentalHouse(id: String, onResult: (ApiResponse<String>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.favoriteRentalHouse(id).enqueue(
            createCallback(onResult)
        )
    }

    fun unfavoriteRentalHouse(id: String, onResult: (ApiResponse<String>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.unfavoriteRentalHouse(id).enqueue(
            createCallback(onResult)
        )
    }

    private fun <T> createCallback(onResult: (ApiResponse<T>?) -> Unit) : Callback<ApiResponse<T>> {
        return object : Callback<ApiResponse<T>> {
            override fun onFailure(call: Call<ApiResponse<T>>, t: Throwable) {
                println(t.message.toString())
                onResult(createError(t.message.toString()))
            }
            override fun onResponse( call: Call<ApiResponse<T>>, response: Response<ApiResponse<T>>) {
                if (response.isSuccessful) {
                    val loginResult = response.body()
                    println(loginResult)
                    onResult(loginResult)
                } else {
                    val errorBody = response.errorBody()
                    if(errorBody != null) {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(errorBody.string(), ApiResponse::class.java)
                        onResult(createError(errorResponse.error.toString()))
                    } else {
                        onResult(createError("Bilinmeyen bir hata oluştu."))
                    }
                }
            }
        }
    }
}