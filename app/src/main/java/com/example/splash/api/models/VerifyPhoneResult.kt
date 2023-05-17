package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class VerifyPhoneResult(
    @SerializedName("phone") val phone: String
)