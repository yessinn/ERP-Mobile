package com.allsoft.erpmobile.data.remote.dto

/**
 * Réponse de POST /api/auth/register (correspond à UserResponse côté backend).
 * Ne contient jamais le mot de passe.
 */
data class RegisterResponse(
    val id: Long,
    val email: String,
    val role: String
)