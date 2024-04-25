package com.example.galibaapp_semestralka.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.galibaapp_semestralka.screens.LoginScreen
import com.example.galibaapp_semestralka.screens.RegisterScreen



@ExperimentalMaterial3Api
@Composable
fun LoginRegisterNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.REGISTER.name
    ) {
        composable(route = Screens.LOGIN.name) { LoginScreen(navController) }
        composable(route = Screens.REGISTER.name) { RegisterScreen(navController) }
        composable(route = Screens.HOME.name) { BottomNavBar() }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun Prev() {
    LoginRegisterNavigation()
}
