package com.allsoft.erpmobile.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.allsoft.erpmobile.data.local.TokenManager
import com.allsoft.erpmobile.data.repository.AuthRepository
import com.allsoft.erpmobile.ui.home.HomeScreen
import com.allsoft.erpmobile.ui.login.LoginScreen
import com.allsoft.erpmobile.ui.login.LoginViewModelFactory
import com.allsoft.erpmobile.ui.register.RegisterScreen
import com.allsoft.erpmobile.ui.register.RegisterViewModelFactory

private object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
}

@Composable
fun AppNavigation(
    authRepository: AuthRepository,
    tokenManager: TokenManager
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                viewModelFactory = LoginViewModelFactory(authRepository),
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        // On retire login de la pile pour empêcher un retour arrière vers l'écran de connexion
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                viewModelFactory = RegisterViewModelFactory(authRepository),
                onRegisterSuccess = {
                    // Après inscription, on renvoie vers login pour que l'utilisateur se connecte
                    // avec son nouveau compte (cohérent avec le fait que register ne renvoie pas de token).
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                onLogout = {
                    tokenManager.clearToken()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }
    }
}