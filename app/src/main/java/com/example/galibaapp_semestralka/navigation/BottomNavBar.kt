package com.example.galibaapp_semestralka.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.galibaapp_semestralka.screens.FollowScreen
import com.example.galibaapp_semestralka.screens.HomeScreen.HomeScreen

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItem("Home", Icons.Default.Home, Screens.HOME.name),
    NavItem("Follow", Icons.Default.Favorite, Screens.FOLLOW.name)
)

@Composable
fun BottomNavBar() {
    val navController : androidx.navigation.NavController = rememberNavController()

    var selected: Screens = Screens.HOME
    Scaffold (

        floatingActionButton = {

            FloatingActionButton(
                onClick = { /*TODO*/ },
                //containerColor = secondaryContainerLight
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
                               },


        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRouteDestination = navBackStackEntry?.destination


          NavigationBar {


                listOfNavItems.forEach { navItem ->
                    NavigationBarItem (
                        //colors = NavigationBarItemDefaults.colors(),
                        selected = currentRouteDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(imageVector = navItem.icon, contentDescription = null) },
                        label = { Text(text = navItem.label) }
                    )
                }
          }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController as NavHostController,
            startDestination = Screens.HOME.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.HOME.name) {
                selected = Screens.HOME
                HomeScreen()
            }

            composable(route = Screens.FOLLOW.name) {
                selected = Screens.FOLLOW
                FollowScreen()
            }
        }

    }

}



@Preview
@Composable
fun show() {
    BottomNavBar()
}