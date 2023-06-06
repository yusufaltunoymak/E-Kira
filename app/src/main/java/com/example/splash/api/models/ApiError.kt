package com.example.splash.api.models

import org.json.JSONObject

fun <T> createError(error: String, headers: JSONObject? = null): ApiResponse<T> {
    return ApiResponse(
        result = null,
        headers = headers,
        error = error,
        isSuccess = false
    )
}