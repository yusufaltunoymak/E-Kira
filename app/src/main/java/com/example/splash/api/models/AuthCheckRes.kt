package com.example.splash.api.models

import com.google.gson.annotations.SerializedName;

data class AuthCheckRes (
    @SerializedName("user") val user: User
)