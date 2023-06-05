package com.example.splash.api.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("firstName") val firstName: String = "",
    @SerializedName("lastName") val lastName: String = "",
    @SerializedName("validated") val validated: Boolean = false,
    @SerializedName("phoneNumber") val phoneNumber: String = "",
    @SerializedName("profileImage") val profileImage: String? = "",
    @SerializedName("registerDate") val registerDate: String = ""
)