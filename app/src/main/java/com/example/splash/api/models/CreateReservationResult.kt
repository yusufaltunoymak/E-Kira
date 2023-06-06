package com.example.splash.api.models


import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CreateReservationResult(@SerializedName("end_date")
                                   val endDate: String = "",
                                   @SerializedName("payment_id")
                                   val paymentId: String = "",
                                   @SerializedName("price")
                                   val price: BigDecimal = BigDecimal.ZERO,
                                   @SerializedName("id")
                                   val id: String = "",
                                   @SerializedName("start_date")
                                   val startDate: String = "",
                                   @SerializedName("status")
                                   val status: String = "")