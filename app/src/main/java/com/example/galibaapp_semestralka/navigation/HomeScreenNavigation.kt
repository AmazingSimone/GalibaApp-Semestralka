package com.example.galibaapp_semestralka.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.galibaapp_semestralka.screens.CreateEvent
import com.example.galibaapp_semestralka.screens.EditEvent
import com.example.galibaapp_semestralka.screens.EditUserInfoScreen
import com.example.galibaapp_semestralka.screens.FollowScreen
import com.example.galibaapp_semestralka.screens.HomeScreen.HomeScreen
import com.example.galibaapp_semestralka.screens.ProfileInspectScreen
import com.example.galibaapp_semestralka.screens.UserScreen
import kotlinx.coroutines.launch

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItem("Home", Icons.Default.Home, Screens.HOME.name),
    NavItem("Follow", Icons.Default.Favorite, Screens.FOLLOW.name)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar() {
    val navController : androidx.navigation.NavController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(), 
        color = MaterialTheme.colorScheme.background
    ) {
            //val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
// icons to mimic drawer destinations
    val items = listOf(
        Icons.Default.LocationOn,

        Icons.Default.LocationOn,
        Icons.Default.LocationOn,

        )


        var selectedItem by rememberSaveable { mutableStateOf(0) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
                ) {
//                Column(Modifier.verticalScroll(rememberScrollState())) {
//
//                    Text(text = "Galiba",
//                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
//                        modifier = Modifier.padding(12.dp)
//
//                    )
//                    //HorizontalDivider()
//                    NavigationDrawerItem(
//                        icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) },
//                        label = { Text("Simon Bartanus") },
//                        selected = false,
//                        onClick = {
//                            scope.launch { drawerState.close() }
//                            //selectedItem.value = item
//                            navController.navigate(Screens.PERSONAL_USER_PROFILE.name)
//
//                        },
//                        //modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//                    )
//                    //HorizontalDivider()
//                    Spacer(Modifier.height(12.dp))
//                    items.forEach { item ->
//                        NavigationDrawerItem(
//                            icon = { Icon(item, contentDescription = null) },
//                            label = { Text(item.name) },
//                            selected = item == selectedItem.value,
//                            onClick = {
//                                scope.launch { drawerState.close() }
//                                selectedItem.value = item
//                            },
//                            //modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//                        )
//                        Spacer(Modifier.height(12.dp))
//
//                    }
//                }

                    Column(Modifier.verticalScroll(rememberScrollState())) {

                        Text(
                            text = "Galiba",
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            modifier = Modifier.padding(12.dp)

                        )
                        HorizontalDivider()
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null
                                )
                            },
                            label = { Text("Simon Bartanus") },
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                                //selectedItem.value = item
                                navController.navigate(Screens.PERSONAL_USER_PROFILE.name)

                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )


                        HorizontalDivider()
                        items.forEachIndexed { index, item ->
                            Spacer(Modifier.height(16.dp))
                            NavigationDrawerItem(
                                label = { Text(text = item.name) },
                                selected = index == selectedItem,
                                onClick = {
                                    selectedItem = index
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                icon = {
                                    Icon(imageVector = item, contentDescription = "icon")
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }
                    }
                }
            },
            gesturesEnabled = drawerState.isOpen,
            drawerState = drawerState
        ) {
            Scaffold (

                bottomBar = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRouteDestination = navBackStackEntry?.destination
                    //val selected: String = Screens.HOME.name

                    if (navBackStackEntry?.destination?.route == Screens.HOME.name || navBackStackEntry?.destination?.route == Screens.FOLLOW.name) {

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
                }
            ) { paddingValues ->
                NavHost(
                    navController = navController as NavHostController,
                    startDestination = Screens.HOME.name,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable(route = Screens.HOME.name) {

                        HomeScreen(navController,drawerState)
                    }

                    composable(route = Screens.FOLLOW.name) {
                        FollowScreen(navController)
                    }

                    composable(route = Screens.REGISTER.name) {
                        LoginRegisterNavigation()
                    }

                    composable(route = Screens.PERSONAL_USER_PROFILE.name) {
                        UserScreen(navController)
                    }
                    composable(route = Screens.EDIT_USER_PROFILE.name) {
                        EditUserInfoScreen(navController)
                    }
                    composable(route = Screens.USER_PROFILE.name) {
                        ProfileInspectScreen(navController,"9")
                    }
                    composable(route = Screens.CREATE_EVENT.name) {
                        CreateEvent(navController)
                    }
                    composable(route = Screens.EDIT_EVENT.name) {
                        EditEvent(navController)
                    }
                }

            }
        }    
    }
    


}



@Preview
@Composable
fun show() {
    BottomNavBar()
}