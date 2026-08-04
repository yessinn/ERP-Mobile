package com.allsoft.erpmobile.data.remote.dto

/**
 * Corps de la requête envoyée à POST /api/auth/login
 *
 * ⚠️ À vérifier avec le code réel du backend (AuthController / DTO Kotlin)
 * pour confirmer les noms exacts des champs (email/password vs username, etc.)
 */
data class LoginRequest(
    val email: String,
    val password: String
)