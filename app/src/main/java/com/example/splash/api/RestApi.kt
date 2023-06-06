package com.example.splash.api

import com.example.splash.api.models.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.math.BigDecimal

interface RestApi {
    //====================================================================================================
    @POST("v1/auth/login") @Headers("Content-Type: application/json")
    fun loginPost(@Body data: LoginPost): Call<ApiResponse<LoginRes>>
    //====================================================================================================
    @POST("v1/auth/register") @Headers("Content-Type: application/json")
    fun registerPost(@Body data: RegisterPost): Call<ApiResponse<RegisterRes>>
    //====================================================================================================
    @GET("v1/auth/check") @Headers("Content-Type: application/json")
    fun checkAuth(): Call<ApiResponse<User>>
    //====================================================================================================
    @GET("v1/auth/logout") @Headers("Content-Type: application/json")
    fun logoutAuth(): Call<ApiResponse<String>>
    //====================================================================================================
    @GET("v1/get-cities/1") @Headers("Content-Type: application/json")
    fun getCities(): Call<ApiResponse<List<City>>>
    //====================================================================================================
    @GET("v1/get-towns/{cityId}") @Headers("Content-Type: application/json")
    fun getTowns(@Path(value = "cityId") cityId : Int): Call<ApiResponse<List<Town>>>
    //====================================================================================================
    @GET("v1/get-districts/{townId}") @Headers("Content-Type: application/json")
    fun getDistricts(@Path(value = "townId") townId : Int): Call<ApiResponse<List<District>>>
    //====================================================================================================
    @GET("v1/get-quarters/{districtId}") @Headers("Content-Type: application/json")
    fun getQuarters(@Path(value = "districtId") districtId : Int): Call<ApiResponse<List<Quarter>>>
    //====================================================================================================
    @POST("v1/user/set-phone") @Headers("Content-Type: application/json")
    fun setPhone(@Body data: SetPhonePost): Call<ApiResponse<SetPhoneResult>>
    //====================================================================================================
    @POST("v1/user/verify-phone") @Headers("Content-Type: application/json")
    fun verifyPhone(@Body data: VerifyPhonePost): Call<ApiResponse<VerifyPhoneResult>>
    //====================================================================================================
    @POST("v1/user/set-profile") @Headers("Content-Type: application/json")
    fun setProfile(@Body data: SetProfile): Call<ApiResponse<SetProfile>>
    //====================================================================================================
    @GET("v1/rental-house/list") @Headers("Content-Type: application/json")
    fun getRentalHouseList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
        @Query("search") search: String,
        @Query("favorite") onlyFavorite: Boolean? = null): Call<ApiResponse<RentalHouseList>>
    //====================================================================================================
    @GET("v1/rental-house/owned-list") @Headers("Content-Type: application/json")
    fun getRentalHouseOwnedList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
        @Query("search") search: String): Call<ApiResponse<RentalHouseList>>
    //====================================================================================================
    @GET("v1/rental-house/{id}") @Headers("Content-Type: application/json")
    fun getRentalHouseDetails(
        @Path(value = "id") id : String): Call<ApiResponse<RentalHouseDetails>>
    //====================================================================================================
    @GET("v1/rental-house/{id}/favorite") @Headers("Content-Type: application/json")
    fun favoriteRentalHouse(@Path(value = "id") id : String): Call<ApiResponse<String>>
    //====================================================================================================
    @GET("v1/rental-house/{id}/unfavorite") @Headers("Content-Type: application/json")
    fun unfavoriteRentalHouse(@Path(value = "id") id : String): Call<ApiResponse<String>>
    //====================================================================================================
    @Multipart
    @POST("v1/rental-house/upload-image")
    fun uploadRentalHouseImage(@Part image: MultipartBody.Part): Call<ApiResponse<UploadedImageID>>
    //====================================================================================================
    @Multipart
    @POST("v1/user/set-profile-image")
    fun setProfileImage(@Part image: MultipartBody.Part): Call<ApiResponse<UploadedImageID>>
    //====================================================================================================
    @POST("v1/rental-house/create") @Headers("Content-Type: application/json")
    fun createRentalHouse(@Body data: RentalHouseCreatePost): Call<ApiResponse<RentalHouseDetails>>
    //====================================================================================================
    @POST("v1/rental-house/{id}/reserved-dates") @Headers("Content-Type: application/json")
    fun reservedDatesRentalHouse(@Path(value = "id") id : String): Call<ApiResponse<List<String>>>
    //====================================================================================================
    @POST("v1/reservation/create") @Headers("Content-Type: application/json")
    fun createReservation(@Body data: CreateReservationPost): Call<ApiResponse<CreateReservationResult>>
    //====================================================================================================
    @POST("v1/payment/make") @Headers("Content-Type: application/json")
    fun makePayment(@Body data: PaymentMakePost): Call<ApiResponse<PaymentMakeResult>>
    //====================================================================================================
    @GET("v1/wallet/balance") @Headers("Content-Type: application/json")
    fun getBalance(): Call<ApiResponse<BigDecimal>>
    //====================================================================================================
}