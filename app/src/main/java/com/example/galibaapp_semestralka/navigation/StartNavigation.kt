package com.example.galibaapp_semestralka.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.screens.HomeScreen.HomeScreenNavigation
import com.example.galibaapp_semestralka.screens.LoginScreen
import com.example.galibaapp_semestralka.screens.RegisterScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Start(navController : NavHostController = rememberNavController(), firebaseViewModel: FirebaseViewModel = viewModel()) {

    firebaseViewModel.checkForActiveUser()

    NavHost(
        navController = navController,
        route = Screens.ROOT.name,
        startDestination = if(firebaseViewModel.isUserLoggedIn.value == true) {
            Screens.HOME.name
        } else {
            Screens.AUTHROOT.name
        }
    ) {
        navigation(
            route = Screens.AUTHROOT.name, startDestination = Screens.LOGIN.name
        ) {
            composable(route = Screens.LOGIN.name) {
                LoginScreen(
                    onLoginClick = {
                        navController.popBackStack()
                        navController.navigate(Screens.HOME.name)
                    },

                    onRegisterClick = {
                        navController.navigateToSingleTop(Screens.REGISTER.name)
                    },
                    firebaseViewModel = firebaseViewModel
                )
            }
            composable(route = Screens.REGISTER.name) {
                RegisterScreen(
                    onLoginClick = { navController.navigateToSingleTop(Screens.LOGIN.name) },
                    onRegisterClick = {
                        navController.popBackStack()
                        navController.navigate(Screens.HOME.name)
                    },
                    firebaseViewModel = firebaseViewModel
                )
            }
        }
        composable(route = Screens.HOME.name) {
            HomeScreenNavigation(firebaseViewModel = firebaseViewModel)
        }
    }
}

fun NavHostController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}