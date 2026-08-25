package com.allsoft.erpmobile.data.remote.dto

/**
 * Corps de la requête envoyée à POST /api/auth/register
 */
data class RegisterRequest(
    val email: String,
    val password: String,
    val role: String
)