package com.allsoft.erpmobile.data.remote.dto

/**
 * Réponse attendue de POST /api/auth/login en cas de succès.
 *
 * ⚠️ À ajuster selon la vraie structure JSON renvoyée par AuthController.login()
 */
data class LoginResponse(
    val token: String,
    val email: String? = null,
    val role: String? = null
)
