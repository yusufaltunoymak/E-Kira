package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class CreateReservationPost(@SerializedName("end_date")
                                 val endDate: String = "",
                                 @SerializedName("rental_house_id")
                                 val rentalHouseId: String = "",
                                 @SerializedName("identity_number")
                                 val identityNumber: String = "",
                                 @SerializedName("start_date")
                                 val startDate: String = "")