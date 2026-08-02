package com.allsoft.erp.data.repository

import com.allsoft.erp.data.model.LoginRequest
import com.allsoft.erp.data.model.LoginResponse
import com.allsoft.erp.data.network.RetrofitInstance
import com.allsoft.erp.utils.Resource

class AuthRepository {

    suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return try {
            val response = RetrofitInstance.api.login(
                LoginRequest(email = email, password = password)
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Empty response from server")
            } else {
                when (response.code()) {
                    401 -> Resource.Error("Invalid email or password")
                    403 -> Resource.Error("Account suspended. Contact admin")
                    404 -> Resource.Error("Account not found")
                    500 -> Resource.Error("Server error. Try again later")
                    else -> Resource.Error("Login failed (${response.code()})")
                }
            }
        } catch (e: java.net.UnknownHostException) {
            Resource.Error("No internet connection")
        } catch (e: java.net.SocketTimeoutException) {
            Resource.Error("Connection timed out")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unexpected error occurred")
        }
    }
}
