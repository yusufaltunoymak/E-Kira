package com.example.splash.api.models

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class RentalHouseCreatePost(@SerializedName("min_day")
                                 val minDay: Int = 0,
                                 @SerializedName("g_coordinate")
                                 val gCoordinate: String = "",
                                 @SerializedName("rent_period")
                                 val rentPeriod: Int = 0,
                                 @SerializedName("price")
                                 val price: BigDecimal = BigDecimal.ZERO,
                                 @SerializedName("imageUUIDs")
                                 val imageUUIDs: List<String>?,
                                 @SerializedName("description")
                                 val description: String = "",
                                 @SerializedName("commision_type")
                                 var commisionType: Int = 0,
                                 @SerializedName("title")
                                 val title: String = "",
                                 @SerializedName("quarter_id")
                                 val quarterId: Int = 0)