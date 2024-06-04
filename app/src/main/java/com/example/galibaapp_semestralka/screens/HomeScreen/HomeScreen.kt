@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.galibaapp_semestralka.screens.HomeScreen

//import com.example.galibaapp_semestralka.navigation.listOfNavItems
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.Event
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.navigation.Screens
import com.example.galibaapp_semestralka.navigation.Start
import com.example.galibaapp_semestralka.screens.CreateEvent
import com.example.galibaapp_semestralka.screens.EditEvent
import com.example.galibaapp_semestralka.screens.EditUserInfoScreen
import com.example.galibaapp_semestralka.screens.FollowScreen
import com.example.galibaapp_semestralka.screens.ProfileInspectScreen
import com.example.galibaapp_semestralka.screens.UserScreen
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(
    navController: NavController,
    drawerState: DrawerState,// = rememberDrawerState(initialValue = DrawerValue.Closed),
    firebaseViewModel: FirebaseViewModel// = viewModel()
) {

    val username by firebaseViewModel.username.observeAsState()



    LaunchedEffect(Unit) {
        firebaseViewModel.getCurrentUserData()
        firebaseViewModel.getAllEventsCreated()
        //firebaseViewModel.getAllEventsCreated(byCity = firebaseViewModel.chosenCity)
    }


    val scope = rememberCoroutineScope()



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Scaffold(

            topBar = {

//                    Surface(
//                    ) {


                Column(
                    modifier = Modifier
                        //.fillMaxSize()
                        //

                        .padding(20.dp)
                        .fillMaxWidth(),
                    //.verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.Start,

                    ) {

//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(start = 20.dp, top = 20.dp)
//                            .verticalScroll(rememberScrollState()),
//
//                        ) {

                    Text(
                        text = "Ahoj, $username !",
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                        //color = MaterialTheme.colorScheme.onSurface
                    )

                    Box(Modifier.clickable {

                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Banska Bystrica, Slovensko",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            )
                            IconButton(

                                onClick = { /*TODO*/ }) {

                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = ""
                                )
                            }
                        }
                    }


                    var text by remember { mutableStateOf("") }
                    var active by remember { mutableStateOf(false) }
                    var items = remember {
                        mutableStateListOf(
                            ""
                        )
                    }
                    SearchBar(

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
                                scope.launch { drawerState.open() }
                            }) {
                                //M3SearchNavDrawer()
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
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
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

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        //modifier = Modifier.padding(vertical = 10.dp),
                        text = "Galiby v okoli:",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        //color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    val eventList = firebaseViewModel.allEvents

                    Log.d("userEvents","posledny event : ${eventList.size}")


                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .verticalScroll(rememberScrollState())
                    ) {

                        //Spacer(modifier = Modifier.height(15.dp))

                        for (event in eventList) {
                            //Spacer(modifier = Modifier.height(15.dp))

                            CustomCard(
                                firebaseViewModel = firebaseViewModel,
                                navController = navController,
                                image = R.drawable.backonlabelpfp,
                                event = event,
                                profilePic = R.drawable.backonlabelpfp
                            )
                        }
                    }


                    // FIREBASE ///


//                    val eventList = firebaseViewModel.allEvents
//                    Log.d("Firebaseviewmodel", "${eventList.size}")
//                    Column(
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(20.dp))
//                    ) {
//
//                        for (event in eventList) {
//                            CustomCard(
//                                firebaseViewModel = firebaseViewModel,
//                                navController = navController,
//                                image = R.drawable.backonlabelpfp,
//                                title = event?.nazov.toString(),
//                                location = event?.miesto.toString(),
//                                city = event?.mesto?.nazov.toString(),
//                                cityPrefix = event?.mesto?.skratka.toString(),
//                                date = event?.datumACas?.toLocalDate()?.format(DateTimeFormatter.ofPattern("d MMM")) .toString(),
//                                time = event?.datumACas?.toLocalTime().toString(),
//                                text = event?.popis.toString(),
//                                author = firebaseViewModel.username.value.toString(),
//                                profilePic = R.drawable.backonlabelpfp
//                            )
//                        }
//                    }
                    Spacer(modifier = Modifier.height(15.dp))


                    //}
                    //}
                    //}
                }
                //}

            },
            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //TODO 0
            // OBRAZOVKY HOME SCREEN A FOLLOW SCREEN SU LEN PREKRYTE SPODNYN MAVIGACNYM BAROM NEDA SA DOSTAT NA UPLNY KONIEC LISTU

            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


        ) {}

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenNavigation(
    navController: NavHostController = rememberNavController(),
    //oldNavController: NavHostController,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    firebaseViewModel: FirebaseViewModel
) {


    val username by firebaseViewModel.username.observeAsState()
    val isArtist by firebaseViewModel.isArtist.observeAsState()

    LaunchedEffect(Unit) {
        firebaseViewModel.getCurrentUserData()
    }
    val scope = rememberCoroutineScope()
    // icons to mimic drawer destinations
    val items = listOf(
        Icons.Default.LocationOn,
        Icons.Default.LocationOn,
        Icons.Default.LocationOn,
    )
    var selectedItem by rememberSaveable { mutableStateOf(0) }


    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
            ) {

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
                        label = { Text("@" + username) ?: "" },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            //selectedItem.value = item
                            navController.navigate(Screens.PERSONAL_USER_PROFILE.name) {
                                launchSingleTop = true
                            }

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
                                Icon(
                                    imageVector = item,
                                    contentDescription = "icon"
                                )
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

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRouteDestination = navBackStackEntry?.destination

        Scaffold(
            bottomBar = {


                if (navBackStackEntry?.destination?.route == Screens.HOME.name || navBackStackEntry?.destination?.route == Screens.FOLLOW.name) {

                    data class NavItem(
                        val label: String,
                        val icon: ImageVector,
                        val route: String
                    )

                    val listOfNavItems = listOf(
                        NavItem("Home", Icons.Default.Home, Screens.HOME.name),
                        NavItem("Follow", Icons.Default.Favorite, Screens.FOLLOW.name)
                    )

                    NavigationBar {

                        listOfNavItems.forEach { navItem ->
                            NavigationBarItem(
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
                                icon = {
                                    Icon(
                                        imageVector = navItem.icon,
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = navItem.label) }
                            )
                        }
                    }
                }
            },
            floatingActionButton = {

                if (navBackStackEntry?.destination?.route == Screens.HOME.name && isArtist == true) {


                    FloatingActionButton(
                        onClick = {
                            navController.navigate(Screens.CREATE_EVENT.name) {
                                launchSingleTop = true
                            }
                        },

                        ) {
                        Icon(Icons.Filled.Add, "Floating action button.")
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                route = Screens.HOMEROOT.name,
                startDestination = Screens.HOME.name
            ) {
                composable(route = Screens.HOME.name) {
                    HomeScreen(
                        navController = navController,
                        drawerState = drawerState,
                        firebaseViewModel = firebaseViewModel
                    )
                }
                composable(route = Screens.FOLLOW.name) {
                    FollowScreen(navController = navController)
                }
                composable(route = Screens.AUTHROOT.name) {
                    Start()
                }
                composable(route = Screens.USER_PROFILE.name) {
                    ProfileInspectScreen(firebaseViewModel, navController)
                    //tuto pozor aby sa to vymazalo z backstacku...
                }
                composable(route = Screens.CREATE_EVENT.name) {
                    CreateEvent(navController, firebaseViewModel = firebaseViewModel)
                    //aj toto
                }
                composable(route = Screens.EDIT_EVENT.name) {
                    EditEvent(navController, firebaseViewModel = firebaseViewModel)
                    //a toto
                }
                navigation(
                    route = Screens.USER_PROFILE_EDIT_ROOT.name,
                    startDestination = Screens.USER_PROFILE.name
                ) {
                    composable(route = Screens.PERSONAL_USER_PROFILE.name) {
                        UserScreen(navController, firebaseViewModel = firebaseViewModel)
                    }
                    composable(route = Screens.EDIT_USER_PROFILE.name) {
                        EditUserInfoScreen(navController, firebaseViewModel)
                        // A AJ TOTO
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCard(
    //modifier: Modifier = Modifier,
    firebaseViewModel: FirebaseViewModel,
    navController: NavController,
    @DrawableRes image: Int,
    event: Event?,
    @DrawableRes profilePic: Int
) {
    var showFullContent by remember {
        mutableStateOf(false)
    }
    if ((event?.eventDetails?.length ?: 0) < 50) {
        showFullContent = true
    }
    Spacer(modifier = Modifier.height(10.dp))

    Card(
        //colors = CardDefaults.cardColors(surfaceContainerLowLight),
        modifier = Modifier
            .animateContentSize()
            .clickable {
                if ((event?.eventDetails?.length ?: 0) >= 50) {
                    showFullContent = !showFullContent
                }
            },
        shape = RoundedCornerShape(16.dp),
        //colors = MaterialTheme.colorScheme.backgroundz,
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(

                modifier = Modifier.fillMaxSize(),
            ) {
//                Surface(
//                    shape = RoundedCornerShape(24.dp),
//                    modifier = Modifier.wrapContentSize(),
//                ) {
//
//                }
                Box {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                        //horizontalArrangement = Arrangement.SpaceEvenly

                    )
                    {

                        Column(
                            modifier = Modifier
                                .width(200.dp)
                                .padding(end = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(50)
                                    )
                                    .padding(horizontal = 7.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = ("#${event?.city?.skratka}"),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = event?.eventName.toString(),
                                //fontSize = 24.sp,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                ClickableLink(
                                    text = event?.location.toString(),
                                    color = MaterialTheme.colorScheme.secondary,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.ExtraBold
                                )

                                Text(
                                    text = ", ${event?.city?.nazov}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }


                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = event?.dateAndTime?.toLocalDate()
                                    ?.format(DateTimeFormatter.ofPattern("d MMM")).toString(),
                                style = MaterialTheme.typography.headlineMedium
                                //fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "Dvere: ${event?.dateAndTime?.toLocalTime()}",
                                style = MaterialTheme.typography.bodySmall
                                //fontWeight = FontWeight.Bold
                            )

                        }

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.size(width = 100.dp, height = 140.dp)
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(id = image),
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )
                        }

                    }
                }


                //Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(

                        text = event?.eventDetails.toString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = if (showFullContent) 100 else 3
                    )
                    Spacer(modifier = Modifier.height(10.dp))


                    if (!showFullContent) {
                        Text(
                            text = "Citat viac",
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.titleLarge
                        )
                    } else {


                        if (firebaseViewModel.currentUserId.value != event?.userId) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedButton(
                                    modifier = Modifier.padding(end = 10.dp),
                                    onClick = { /*TODO*/ },
                                    //colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryLight, disabledContentColor = primaryLight)
                                ) {
                                    Text("Mam zaujem")
                                }
                                Button(
                                    modifier = Modifier.padding(end = 10.dp),
                                    onClick = { /*TODO*/ },
                                    //colors = ButtonDefaults.buttonColors(primaryLight)
                                ) {
                                    Text(text = "Pridem")
                                }
                            }
                        } else {
                            Button(
                                onClick = {
                                    //firebaseViewModel.currentEventForEdit = event.eventId
                                    Log.d("firebaseviewmodel", event?.eventId.toString())

                                    val onSuccess = {
                                        navController.navigate(Screens.EDIT_EVENT.name) {
                                            launchSingleTop = true
                                        }

                                    }

                                    val onFailure: () -> Unit = {

                                    }
                                    firebaseViewModel.getEventData(
                                        onSuccess,
                                        onFailure,
                                        eventId = event?.eventId.toString()
                                    )


                                    //          TODO 000 UROB VSADE TIETO NACITAVACE

                                    //           if(registerViewModel.registerInProgress.value) {
//                                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//                                    }
                                },
                                //colors = ButtonDefaults.buttonColors(primaryLight)
                            ) {
                                Text(
                                    text = "Upravit",
                                    //fontSize = MaterialTheme.typography.bodySmall.fontSize
                                )
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        onClick = {

                            if (firebaseViewModel.currentUserId.value.toString() != event?.userId.toString()) {
                                val onSuccess = {
                                    navController.navigate(Screens.USER_PROFILE.name) {
                                        launchSingleTop = true
                                    }

                                }

                                val onFailure = {

                                }

                                firebaseViewModel.selectUser(
                                    onSuccess,
                                    onFailure,
                                    event?.userId.toString()
                                )
                            } else {
                                navController.navigate(Screens.PERSONAL_USER_PROFILE.name) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                ) {
                    Image(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = profilePic),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )


                    var username by remember { mutableStateOf("") }

                    var firebaseUserReply = firebaseViewModel.getUserName(
                        usedId = event?.userId.toString(),
                        onSuccess = { fetchedUsername ->
                            username = fetchedUsername
                        },
                        onFailure = {

                        }
                    )

                    Text(
                        modifier = Modifier.padding(start = 6.dp),
                        text = "@${username}",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )

                }

            }
        }
    }
}

@Composable
fun ClickableLink(
    text: String, style: TextStyle = TextStyle.Default,
    color: Color = TextStyle.Default.color,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null
) {
    val formattedText = text.replace(" ", "+")
    val url = "http://maps.google.com/?q=$formattedText"

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = color, fontSize = fontSize, fontWeight = fontWeight)) {
            append(text)
        }
        addStringAnnotation(
            tag = "URL",
            annotation = url,
            start = 0,
            end = text.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        }
    )
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(navController = NavController())
//}


