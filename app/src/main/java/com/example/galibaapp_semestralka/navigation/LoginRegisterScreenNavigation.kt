package com.example.galibaapp_semestralka.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.galibaapp_semestralka.screens.LoginScreen
import com.example.galibaapp_semestralka.screens.RegisterScreen


@ExperimentalMaterial3Api
@Composable
fun LoginRegisterNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login-navigation"
    ) {

        navigation(startDestination = Screens.LOGIN.name,route = "login-navigation") {
                composable(route = Screens.LOGIN.name) { LoginScreen(
                    onLoginClick = {
                        navController.navigate(Screens.HOME.name) {
                            popUpTo(route = "login-navigation")
                        }
                    },
                    onRegisterClick = {
                        navController.navigateToSingleTop(Screens.REGISTER.name)
                    }) }
                composable(route = Screens.REGISTER.name) { RegisterScreen(
                    onLoginClick = {
                        navController.navigateToSingleTop(Screens.LOGIN.name)
                    },
                    onRegisterClick = {
                        navController.navigate(Screens.HOME.name) {
                            popUpTo(route = "login-navigation")

                        }
                    }
                )}

            }
        composable(route = Screens.HOME.name) { BottomNavBar() }
    }
}

fun NavController.navigateToSingleTop(route:String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun Prev() {
    LoginRegisterNavigation()
}
