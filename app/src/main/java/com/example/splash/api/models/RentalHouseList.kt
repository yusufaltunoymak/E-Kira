package com.example.splash.api.models


import com.google.gson.annotations.SerializedName

data class RentalHouseImage(@SerializedName("width")
                            val width: Int = 0,
                            @SerializedName("url")
                            val url: String = "",
                            @SerializedName("height")
                            val height: Int = 0)

data class Pagination(@SerializedName("next_page")
                      val nextPage: Boolean = false,
                      @SerializedName("full_count")
                      var fullCount: Int = 0,
                      @SerializedName("total_count")
                      var totalCount: Int = 0,
                      @SerializedName("prev_page")
                      val prevPage: Boolean = false)

data class Address(@SerializedName("district_name")
                   val districtName: String = "",
                   @SerializedName("city_name")
                   val cityName: String = "",
                   @SerializedName("town_id")
                   val townId: Int = 0,
                   @SerializedName("town_name")
                   val townName: String = "",
                   @SerializedName("country_name")
                   val countryName: String = "",
                   @SerializedName("district_id")
                   val districtId: Int = 0,
                   @SerializedName("country_id")
                   val countryId: Int = 0,
                   @SerializedName("quarter_id")
                   val quarterId: Int = 0,
                   @SerializedName("city_id")
                   val cityId: Int = 0,
                   @SerializedName("quarter_name")
                   val quarterName: String = "")


data class ResultsItem(@SerializedName("min_day")
                       val minDay: Int = 0,
                       @SerializedName("images")
                       val images: List<List<RentalHouseImage>>,
                       @SerializedName("address")
                       val address: Address,
                       @SerializedName("rent_period")
                       val rentPeriod: Int = 0,
                       @SerializedName("price")
                       val price: Int = 0,
                       @SerializedName("id")
                       val id: String = "",
                       @SerializedName("commision_type")
                       val commisionType: String = "",
                       @SerializedName("title")
                       val title: String = "",
                       @SerializedName("favorite")
                       var favorite: Boolean = false)


data class RentalHouseList(
    var fetched: Boolean = false, @SerializedName("pagination")
                           val pagination: Pagination,
    @SerializedName("results")
                           val results: ArrayList<ResultsItem>
)


fun GetRentPeriod(rentPeriod: Int): String {
    return when (rentPeriod) {
        1 -> "Günlük"
        2 -> "Aylık"
        3 -> "Yıllık"
        else -> "Belirtilmemiş"
    }
}