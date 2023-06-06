package com.example.splash.api.models

import org.json.JSONObject

fun <T> createError(error: String): ApiResponse<T> {
    return ApiResponse(
        result = null,
        headers = null,
        error = error,
        isSuccess = false
    )
}