package com.example.splash.api.models


import com.google.gson.annotations.SerializedName

data class TownFull(@SerializedName("city")
                val city: CityFull,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("display_order")
                val displayOrder: Int = 0,
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("sort_order")
                val sortOrder: Int = 0,
                @SerializedName("city_id")
                val cityId: Int = 0)


data class AddressFull(@SerializedName("district")
                       val district: DistrictFull,
                       @SerializedName("name")
                       val name: String = "",
                       @SerializedName("display_order")
                       val displayOrder: Int = 0,
                       @SerializedName("id")
                       val id: Int = 0,
                       @SerializedName("district_id")
                       val districtId: Int = 0,
                       @SerializedName("detail")
                       val detail: String = "",
                       @SerializedName("sort_order")
                       val sortOrder: Int = 0)


data class CountryFull(@SerializedName("name")
                   val name: String = "",
                   @SerializedName("display_order")
                   val displayOrder: Int = 0,
                   @SerializedName("language")
                   val language: String = "",
                   @SerializedName("id")
                   val id: Int = 0,
                   @SerializedName("abbreviation")
                   val abbreviation: String = "",
                   @SerializedName("alpha3_code")
                   val alphaCode3: String = "",
                   @SerializedName("sort_order")
                   val sortOrder: Int = 0,
                   @SerializedName("alpha2_code")
                   val alphaCode2: String = "",
                   @SerializedName("phone_code")
                   val phoneCode: String = "")


data class CityFull(@SerializedName("country")
                val country: CountryFull,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("display_order")
                val displayOrder: Int = 0,
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("tag")
                val tag: String = "",
                @SerializedName("sort_order")
                val sortOrder: Int = 0,
                @SerializedName("country_id")
                val countryId: Int = 0)


data class DistrictFull(@SerializedName("town")
                    val town: TownFull,
                    @SerializedName("town_id")
                    val townId: Int = 0,
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("display_order")
                    val displayOrder: Int = 0,
                    @SerializedName("id")
                    val id: Int = 0,
                    @SerializedName("sort_order")
                    val sortOrder: Int = 0)


