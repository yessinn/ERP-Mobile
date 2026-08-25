package com.allsoft.erpmobile.data.remote

import com.allsoft.erpmobile.data.remote.dto.LoginRequest
import com.allsoft.erpmobile.data.remote.dto.RegisterRequest
import com.allsoft.erpmobile.data.remote.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<String>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}