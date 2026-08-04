package com.allsoft.erpmobile.data.repository

import com.allsoft.erpmobile.data.local.TokenManager
import com.allsoft.erpmobile.data.remote.AuthApi
import com.allsoft.erpmobile.data.remote.dto.LoginRequest
import java.io.IOException

sealed class AuthResult {
    data class Success(val token: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthRepository(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) {

    suspend fun login(email: String, password: String): AuthResult {
        return try {
            val response = authApi.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                val token = response.body()

                if (token.isNullOrBlank()) {
                    AuthResult.Error("Réponse du serveur invalide (token manquant)")
                } else {
                    tokenManager.saveToken(token)
                    AuthResult.Success(token)
                }
            } else {
                val message = if (response.code() == 401) {
                    "Email ou mot de passe incorrect"
                } else {
                    "Erreur serveur (code ${response.code()})"
                }
                AuthResult.Error(message)
            }
        } catch (e: IOException) {
            AuthResult.Error("Impossible de contacter le serveur. Vérifie ta connexion.")
        } catch (e: Exception) {
            AuthResult.Error("Une erreur inattendue est survenue : ${e.message}")
        }
    }
}