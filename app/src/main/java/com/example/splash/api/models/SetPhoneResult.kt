package com.example.splash.api.models;

import com.google.gson.annotations.SerializedName;

data class SetPhoneResult (
    @SerializedName("timeout") val timeout: Int
)