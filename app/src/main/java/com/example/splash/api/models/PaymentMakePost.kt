package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class PaymentMakePost(@SerializedName("payment_id")
                           val paymentId: String = "")