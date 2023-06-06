package com.example.splash.api.models

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class ApiResponse<T>(
    @SerializedName("result") var result: T? = null,
    @SerializedName("headers") var headers: JSONObject?,
    @SerializedName("error") var error: String? = "",
    @SerializedName("isSuccess") var isSuccess: Boolean = false,
)