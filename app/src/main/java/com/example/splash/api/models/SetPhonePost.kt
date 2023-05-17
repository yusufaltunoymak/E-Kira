package com.example.splash.api.models

import android.text.Editable
import com.google.gson.annotations.SerializedName

data class SetPhonePost(
    @SerializedName("phone") val phone: String
)