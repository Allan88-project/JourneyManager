package com.allan88.journeymanager.network

data class ApiResponse<T>(
    val success: Boolean,
    val data: T
)