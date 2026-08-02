package com.allsoft.erp.navigation

import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.allsoft.erp.ui.screens.HomeScreen
import com.allsoft.erp.ui.screens.LoginScreen

object Routes {
    const val LOGIN = "login"
    const val HOME  = "home/{token}/{role}"

    fun homeRoute(token: String, role: String) =
        "home/$token/$role"
}

@Composable
fun ERPNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.LOGIN
) {
    NavHost(
        navController    = navController,
        startDestination = startDestination
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { token, role ->
                    navController.navigate(Routes.homeRoute(token, role)) {
                        // Clear back stack so user can't go back to login
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Routes.HOME,
            arguments = listOf(
                navArgument("token") { type = NavType.StringType },
                navArgument("role")  { type = NavType.StringType }
            )
        ) { backStackEntry ->
            HomeScreen(
                token = backStackEntry.arguments?.getString("token") ?: "",
                role  = backStackEntry.arguments?.getString("role")  ?: ""
            )
        }
    }
}
