package com.example.splash.api

import android.content.Context
import com.example.splash.api.models.*
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

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

    fun getRentalHouseOwnedList(page: Int, limit: Int, sort: String, search: String, onResult: (ApiResponse<RentalHouseList>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getRentalHouseOwnedList(page, limit, sort, search).enqueue(
            createCallback(onResult)
        )
    }

    fun getRentalHouseDetails(rentalHouseID: String, onResult: (ApiResponse<RentalHouseDetails>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getRentalHouseDetails(rentalHouseID).enqueue(
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

    fun uploadRentalHouseImage(image: MultipartBody.Part, onResult: (ApiResponse<UploadedImageID>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.uploadRentalHouseImage(image).enqueue(
            createCallback(onResult)
        )
    }

    fun setProfileImage(image: MultipartBody.Part, onResult: (ApiResponse<UploadedImageID>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.setProfileImage(image).enqueue(
            createCallback(onResult)
        )
    }

    fun createRentalHouse(data: RentalHouseCreatePost, onResult: (ApiResponse<RentalHouseDetails>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.createRentalHouse(data).enqueue(
            createCallback(onResult)
        )
    }

    fun reservedDatesRentalHouse(id: String, onResult: (ApiResponse<List<String>>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.reservedDatesRentalHouse(id).enqueue(
            createCallback(onResult)
        )
    }

    fun createReservation(data: CreateReservationPost, onResult: (ApiResponse<CreateReservationResult>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.createReservation(data).enqueue(
            createCallback(onResult)
        )
    }

    fun makePayment(data: PaymentMakePost, onResult: (ApiResponse<PaymentMakeResult>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.makePayment(data).enqueue(
            createCallback(onResult)
        )
    }

    fun getBalance(onResult: (ApiResponse<BigDecimal>?) -> Unit) {
        val retrofit = ServiceBuilder(this.mContext).buildService(RestApi::class.java)
        retrofit.getBalance().enqueue(
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
                    val stringResponse = response.body().toString()
                    println(stringResponse)
                    onResult(loginResult)
                } else {
                    val errorBody = response.errorBody()
                    if(errorBody != null) {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(errorBody.string(), ApiResponse::class.java)
                        println(errorBody.string())
                        onResult(createError(errorResponse.error.toString(), errorResponse.headers))
                    } else {
                        onResult(createError("Bilinmeyen bir hata oluştu."))
                    }
                }
            }
        }
    }
}