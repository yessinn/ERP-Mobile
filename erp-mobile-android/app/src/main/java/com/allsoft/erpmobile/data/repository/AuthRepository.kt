package com.allsoft.erpmobile.data.repository

import com.allsoft.erpmobile.data.local.TokenManager
import com.allsoft.erpmobile.data.remote.AuthApi
import com.allsoft.erpmobile.data.remote.dto.LoginRequest
import com.allsoft.erpmobile.data.remote.dto.RegisterRequest
import java.io.IOException

sealed class AuthResult {
    data class Success(val token: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

sealed class RegisterResult {
    data class Success(val email: String) : RegisterResult()
    data class Error(val message: String) : RegisterResult()
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

    suspend fun register(email: String, password: String, role: String): RegisterResult {
        return try {
            val response = authApi.register(RegisterRequest(email, password, role))

            if (response.isSuccessful) {
                val body = response.body()

                if (body == null) {
                    RegisterResult.Error("Réponse du serveur invalide")
                } else {
                    RegisterResult.Success(body.email)
                }
            } else {
                // ⚠️ Code exact pour "email déjà utilisé" pas encore confirmé avec Yessin.
                // On gère un cas générique en attendant (à ajuster une fois testé).
                val message = when (response.code()) {
                    409 -> "Cet email est déjà utilisé"
                    400 -> "Données invalides"
                    else -> "Erreur serveur (code ${response.code()})"
                }
                RegisterResult.Error(message)
            }
        } catch (e: IOException) {
            RegisterResult.Error("Impossible de contacter le serveur. Vérifie ta connexion.")
        } catch (e: Exception) {
            RegisterResult.Error("Une erreur inattendue est survenue : ${e.message}")
        }
    }
}