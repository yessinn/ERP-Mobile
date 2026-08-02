
package com.allsoft.erp.data.network

import com.allsoft.erp.data.model.LoginRequest
import com.allsoft.erp.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login") // ← Change to your actual endpoint
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}
