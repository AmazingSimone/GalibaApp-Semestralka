package com.example.galibaapp_semestralka.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
fun M3Search() {

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var items = remember {
        mutableStateListOf(
            ""
        )
    }
    SearchBar (
        //colors = SearchBarDefaults.colors(surfaceContainerLight),
        query = text,
        onQueryChange = {
            text = it
        },
        onSearch = {
            items.add(text)
            active = false
            text = ""
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = { Text(text = "Hladaj Galibu") },
        leadingIcon = {
            IconButton(onClick = {

            }) {

                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (text.isNotEmpty()) {
                            text = ""
                        } else {
                            active = false
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "close"
                )
            } else {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }

        }
    ) {
        items.forEach {
            Row(modifier = Modifier.padding(all = 14.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "history icon"
                )
                Text(text = it)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3SearchNavDrawer() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
// icons to mimic drawer destinations
    val items = listOf(
        Icons.Default.AccountCircle,

        Icons.Default.Email,
        Icons.Default.Favorite,

        )


    val selectedItem = remember { mutableStateOf(items[0]) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet() {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Spacer(Modifier.height(12.dp))
                    items.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item, contentDescription = null) },
                            label = { Text(item.name) },
                            selected = item == selectedItem.value,
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedItem.value = item
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        },
        content = {
            Surface (
            color = MaterialTheme.colorScheme.background
        ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
//                Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
//                Spacer(Modifier.height(20.dp))
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "menuIcon")
                    //Text("Click to open")
                }

//                    Column (
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 20.dp, top = 20.dp),
//
//                    ){
//
//                        Text(
//                            modifier = Modifier.padding(vertical = 0.dp),
//                            text = "Ahoj, Šimon!",
//                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
//                            //color = MaterialTheme.colorScheme.onSurface
//                        )
//
//                        Box (Modifier.clickable {
//
//                        }) {
//                            Row (
//                                verticalAlignment = Alignment.CenterVertically
//                            ){
//                                Text(
//                                    text = "Banska Bystrica, Slovensko",
//                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
//                                    //color = MaterialTheme.colorScheme.onSurface,
//
//                                )
//                                IconButton(
//
//                                    onClick = { /*TODO*/ }) {
//
//                                    Icon(
//                                        imageVector = Icons.Default.LocationOn,
//                                        contentDescription = ""
//                                    )
//                                }
//                            }
//                        }
//                    }
//
//                    var text by remember { mutableStateOf("") }
//                    var active by remember { mutableStateOf(false) }
//                    var items = remember {
//                        mutableStateListOf(
//                            ""
//                        )
//                    }
//                    SearchBar (
//                        //colors = SearchBarDefaults.colors(surfaceContainerLight),
//                        query = text,
//                        onQueryChange = {
//                            text = it
//                        },
//                        onSearch = {
//                            items.add(text)
//                            active = false
//                            text = ""
//                        },
//                        active = active,
//                        onActiveChange = {
//                            active = it
//                        },
//                        placeholder = { Text(text = "Hladaj Galibu") },
//                        leadingIcon = {
//                            IconButton(onClick = {
//                                scope.launch { drawerState.open()}
//                            }) {
//
//                                Icon(
//                                    imageVector = Icons.Default.Menu,
//                                    contentDescription = "Menu"
//                                )
//                            }
//                        },
//                        trailingIcon = {
//                            if (active) {
//                                Icon(
//                                    modifier = Modifier.clickable {
//                                        if (text.isNotEmpty()) {
//                                            text = ""
//                                        } else {
//                                            active = false
//                                        }
//                                    },
//                                    imageVector = Icons.Default.Close,
//                                    contentDescription = "close"
//                                )
//                            } else {
//                                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
//                            }
//
//                        }
//                    ) {
//                        items.forEach {
//                            Row(modifier = Modifier.padding(all = 14.dp)) {
//                                Icon(
//                                    modifier = Modifier.padding(end = 10.dp),
//                                    imageVector = Icons.Default.Refresh,
//                                    contentDescription = "history icon"
//                                )
//                                Text(text = it)
//                            }
//                        }
//                    }

                    //BottomNavBar()

                }
            }
        }
    )

}

//@Composable
//fun NavDrawerScreenNavigation(
//) {
//    val navController = rememberNavController();
//    NavHost(
//        navController = navController,
//        startDestination = Screens.HOME.name,
//    ) {
//        composable(route = Screens.HOME.name) {
//            HomeScreen(navController)
//        }
//        composable(route = Screens.PERSONAL_PROFILE.name) {
//            UserScreen(navController)
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar() {
    val navController : androidx.navigation.NavController = rememberNavController()

    Scaffold (

 //       floatingActionButton = {

//            FloatingActionButton(
//                onClick = { /*TODO*/ },
//                //containerColor = secondaryContainerLight
//        ) {
//            Icon(Icons.Filled.Add, "Floating action button.")
//        } },


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

                HomeScreen(navController)
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



@Preview
@Composable
fun show() {
    BottomNavBar()
}