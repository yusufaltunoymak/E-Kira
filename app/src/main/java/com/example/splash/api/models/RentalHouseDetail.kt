package com.example.splash.api.models


import com.google.gson.annotations.SerializedName
import java.math.BigDecimal


data class RentalHouseDetailCreator(@SerializedName("profile_image")
                   val profileImage: String? = null,
                   @SerializedName("phone")
                   val phone: String = "",
                   @SerializedName("last_name")
                   val lastName: String = "",
                   @SerializedName("id")
                   val id: String = "",
                   @SerializedName("first_name")
                   val firstName: String = "",
                   @SerializedName("email")
                   val email: String = "")


data class RentalHouseDetails(@SerializedName("min_day")
                             val minDay: Int = 0,
                              @SerializedName("creator")
                             val creator: RentalHouseDetailCreator,
                              @SerializedName("rent_period")
                             val rentPeriod: Int = 0,
                              @SerializedName("address")
                             val address: AddressFull,
                              @SerializedName("price")
                             val price: BigDecimal = BigDecimal.ZERO,
                              @SerializedName("description")
                             val description: String = "",
                              @SerializedName("images")
                             val images: List<List<UploadedImage>>,
                              @SerializedName("id")
                             val id: String = "",
                              @SerializedName("published")
                             val published: Boolean = false,
                              @SerializedName("title")
                             val title: String = "",
                              @SerializedName("commision_type")
                             val commisionType: String = "")


