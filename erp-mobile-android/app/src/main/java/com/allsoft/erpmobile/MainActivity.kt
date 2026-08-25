package com.allsoft.erpmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.allsoft.erpmobile.data.local.TokenManager
import com.allsoft.erpmobile.data.remote.RetrofitClient
import com.allsoft.erpmobile.data.repository.AuthRepository
import com.allsoft.erpmobile.ui.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenManager = TokenManager(applicationContext)
        val authApi = RetrofitClient.create(tokenManager)
        val authRepository = AuthRepository(authApi, tokenManager)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(
                        authRepository = authRepository,
                        tokenManager = tokenManager
                    )
                }
            }
        }
    }
}