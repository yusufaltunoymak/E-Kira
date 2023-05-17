package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class SetProfile(
    @SerializedName("first_name") val first_name: String,
    @SerializedName("last_name") val last_name: String
)