package com.example.splash.api.models

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class PaymentMakeResult(@SerializedName("amount")
                             val amount: BigDecimal = BigDecimal.ZERO,
                             @SerializedName("stripe")
                             val stripe: PaymentMakeResultStripe,
                             @SerializedName("commision")
                             val commision: Boolean = false,
                             @SerializedName("amount_gross")
                             val amountGross: BigDecimal = BigDecimal.ZERO)

data class PaymentMakeResultStripe(@SerializedName("client_secret")
                  val clientSecret: String = "")