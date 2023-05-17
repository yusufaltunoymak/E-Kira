package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class VerifyPhonePost(
    @SerializedName("code") val code: String,
    @SerializedName("phone") val phone: String
)