package Jar.dto

import Jar.model.User

/**
 * DTO de réponse pour l'inscription — ne contient JAMAIS le mot de passe,
 * même chiffré, pour éviter de l'exposer inutilement dans les réponses API.
 */
data class UserResponse(
    val id: Long,
    val email: String,
    val role: String
) {
    companion object {
        fun fromUser(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                email = user.email,
                role = user.role
            )
        }
    }
}