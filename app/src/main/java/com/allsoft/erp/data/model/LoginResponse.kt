package com.allsoft.erp.data.model

data class LoginResponse(
    val token: String,
    val userId: String,
    val fullName: String,
    val role: String,
    val message: String
)
