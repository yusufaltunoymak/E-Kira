package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class Country(@SerializedName("display_order")
                   val displayOrder: Int = 0,
                   @SerializedName("name")
                   val name: String = "",
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

data class City(@SerializedName("country")
                val country: Country,
                @SerializedName("display_order")
                val displayOrder: Int = 0,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("tag")
                val tag: String = "",
                @SerializedName("sort_order")
                val sortOrder: Int = 0,
                @SerializedName("country_id")
                val countryId: Int = 0)

data class Town(@SerializedName("city")
                val city: City,
                @SerializedName("display_order")
                val displayOrder: Int = 0,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("id")
                val id: Int = 0,
                @SerializedName("sort_order")
                val sortOrder: Int = 0,
                @SerializedName("city_id")
                val cityId: Int = 0)

data class District(@SerializedName("town")
                    val town: Town,
                    @SerializedName("town_id")
                    val townId: Int = 0,
                    @SerializedName("display_order")
                    val displayOrder: Int = 0,
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("id")
                    val id: Int = 0,
                    @SerializedName("sort_order")
                    val sortOrder: Int = 0)

data class Quarter(@SerializedName("district")
                   val district: District,
                   @SerializedName("display_order")
                   val displayOrder: Int = 0,
                   @SerializedName("name")
                   val name: String = "",
                   @SerializedName("detail")
                   val detail: String = "",
                   @SerializedName("district_id")
                   val districtId: Int = 0,
                   @SerializedName("id")
                   val id: Int = 0,
                   @SerializedName("sort_order")
                   val sortOrder: Int = 0)

