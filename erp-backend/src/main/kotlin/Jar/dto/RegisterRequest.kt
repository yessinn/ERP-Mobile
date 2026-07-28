package Jar.dto

data class RegisterRequest(
    val email: String,
    val password: String,
    val role: String
)