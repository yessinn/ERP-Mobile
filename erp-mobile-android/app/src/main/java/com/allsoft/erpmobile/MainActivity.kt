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
import com.allsoft.erpmobile.ui.login.LoginScreen
import com.allsoft.erpmobile.ui.login.LoginViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Câblage manuel des dépendances (pas de Hilt pour l'instant)
        val tokenManager = TokenManager(applicationContext)
        val authApi = RetrofitClient.create(tokenManager)
        val authRepository = AuthRepository(authApi, tokenManager)
        val loginViewModelFactory = LoginViewModelFactory(authRepository)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(
                        viewModelFactory = loginViewModelFactory,
                        onLoginSuccess = {
                            android.widget.Toast.makeText(
                                applicationContext,
                                "Connexion réussie !",
                                android.widget.Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                }
            }
        }
    }
}