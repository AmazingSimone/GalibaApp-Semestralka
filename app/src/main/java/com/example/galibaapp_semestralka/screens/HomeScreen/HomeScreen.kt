@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.galibaapp_semestralka.screens.HomeScreen

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.navigation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
// icons to mimic drawer destinations
    val items = listOf(
        Icons.Default.LocationOn,

        Icons.Default.LocationOn,
        Icons.Default.LocationOn,

        )


    val selectedItem = remember { mutableStateOf(items[0]) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet() {
                Column(Modifier.verticalScroll(rememberScrollState())) {

                    Text(text = "Galiba",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        modifier = Modifier.padding(12.dp)

                    )
                    HorizontalDivider()
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) },
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
                        Spacer(Modifier.height(12.dp))

                    }

                }
            }
        },

        content = {
            Surface (
                //color = MaterialTheme.colorScheme.background
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
//                Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
//                Spacer(Modifier.height(20.dp))
//                Button(onClick = { scope.launch { drawerState.open() } }) {
//                    Text("Click to open")
//                }

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 20.dp),

                        ){

                        Text(
                            modifier = Modifier.padding(vertical = 0.dp),
                            text = "Ahoj, Šimon!",
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            //color = MaterialTheme.colorScheme.onSurface
                        )

                        Box (Modifier.clickable {

                        }) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = "Banska Bystrica, Slovensko",
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    //color = MaterialTheme.colorScheme.onSurface,

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
                    }

                    var text by remember { mutableStateOf("") }
                    var active by remember { mutableStateOf(false) }
                    var items = remember {
                        mutableStateListOf(
                            ""
                        )
                    }
                    SearchBar (
                        modifier = Modifier.padding(20.dp),
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
                                scope.launch { drawerState.open()}
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

                    //BottomNavBar()



                    Surface {
                        Scaffold (
                            floatingActionButton = {
                                FloatingActionButton(
                                    onClick = { navController.navigate(Screens.CREATE_EVENT.name) },
                                    //containerColor = secondaryContainerLight
                                ) {
                                    Icon(Icons.Filled.Add, "Floating action button.")
                                }
                            }
                        ) {
                            Column(
                                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)
                            )
                            {


//                Column {
//
//                    Text(
//                        modifier = Modifier.padding(vertical = 0.dp),
//                        text = "Ahoj, Šimon!",
//                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
//                        //color = MaterialTheme.colorScheme.onSurface
//                    )
//
//                    Box (Modifier.clickable {
//
//                    }) {
//                        Row (
//                            verticalAlignment = Alignment.CenterVertically
//                        ){
//                            Text(
//                                text = "Banska Bystrica, Slovensko",
//                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
//                                //color = MaterialTheme.colorScheme.onSurface,
//
//                            )
//                            IconButton(
//
//                                onClick = { /*TODO*/ }) {
//
//                                Icon(
//                                    imageVector = Icons.Default.LocationOn,
//                                    contentDescription = ""
//                                )
//                            }
//                        }
//                    }
//                }
//
//                var text by remember { mutableStateOf("") }
//                var active by remember { mutableStateOf(false) }
//                var items = remember {
//                    mutableStateListOf(
//                        ""
//                    )
//                }
//                SearchBar (
//                    //colors = SearchBarDefaults.colors(surfaceContainerLight),
//                    query = text,
//                    onQueryChange = {
//                        text = it
//                    },
//                    onSearch = {
//                        items.add(text)
//                        active = false
//                        text = ""
//                    },
//                    active = active,
//                    onActiveChange = {
//                        active = it
//                    },
//                    placeholder = { Text(text = "Hladaj Galibu") },
//                    leadingIcon = {
//                        IconButton(onClick = {
//                            //scope.launch { drawerState.open()}
//                        }) {
//
//                            Icon(
//                                imageVector = Icons.Default.Menu,
//                                contentDescription = "Menu"
//                            )
//                        }
//                    },
//                    trailingIcon = {
//                        if (active) {
//                            Icon(
//                                modifier = Modifier.clickable {
//                                    if (text.isNotEmpty()) {
//                                        text = ""
//                                    } else {
//                                        active = false
//                                    }
//                                },
//                                imageVector = Icons.Default.Close,
//                                contentDescription = "close"
//                            )
//                        } else {
//                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
//                        }
//
//                    }
//                ) {
//                    items.forEach {
//                        Row(modifier = Modifier.padding(all = 14.dp)) {
//                            Icon(
//                                modifier = Modifier.padding(end = 10.dp),
//                                imageVector = Icons.Default.Refresh,
//                                contentDescription = "history icon"
//                            )
//                            Text(text = it)
//                        }
//                    }
//                }

                                Text(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    text = "Galiby v okoli:",
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    //color = MaterialTheme.colorScheme.onSurface
                                )

                                Column (modifier = Modifier
                                    //.fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                    //verticalArrangement = Arrangement.spacedBy(26.dp, Alignment.CenterVertically),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {

                                    Spacer(modifier = Modifier.height(15.dp))

                                    CustomCard (
                                        navController,
                                        image = R.drawable.backtooldschoolposter,
                                        title = "Back To Oldschool",
                                        location = "Klub 77, Banska Bystrica",
                                        date = "6.5.2023",
                                        text = "Throwback party pre mladých? Aj to je koncept. BackToOldschool sa vracia vo svojej siedmej edícií\uD83D\uDD7A\n" +
                                                ".\n" +
                                                "Hráme šialené mashupy trackov z rokov 60’ až 00’. Ako spolu znie Sara Perche Ti Amo a Du Hast? Alebo Rihanna a Meki Žbirka? ",
                                        autor = "Back On Label",
                                        profilePic = R.drawable.backonlabelpfp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))

                                }
                            }
                        }

                    }



                }
            }
        }
    )



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search() {

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var items = remember {
        mutableStateListOf(
            ""
        )
    }
    SearchBar(

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
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu"
            )
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

@Composable
fun CustomCard(
        //modifier: Modifier = Modifier,
        navController: NavController,
        @DrawableRes image : Int,
        title: String,
        location: String,
        date: String,
        text: String,
        autor: String,
        @DrawableRes profilePic: Int
) {
    var showFullContent by remember {
        mutableStateOf(false)
    }
    Card(
        //colors = CardDefaults.cardColors(surfaceContainerLowLight),
        modifier = Modifier
            .animateContentSize()
            .clickable {
                showFullContent = !showFullContent
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
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.wrapContentSize(),
                ) {

                }
                Row (
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                    //horizontalArrangement = Arrangement.SpaceEvenly

                )
                {


                        Column (
                            modifier = Modifier
                                .width(200.dp)
                                .padding(end = 10.dp)
                        ){
                            Text(
                                text = title,
                                //fontSize = 24.sp,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = location,
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = date,
                                fontWeight = FontWeight.Bold
                                )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(

                                text = text,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = if (showFullContent) 100 else 2
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





                Spacer(modifier = Modifier.height(10.dp))

                if (!showFullContent) {
                    Text(
                        text = "Read More",
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge
                    )
                } else {
                    Column {

                        Row (verticalAlignment = Alignment.CenterVertically){
                            OutlinedButton(
                                modifier = Modifier.padding(end = 10.dp),
                                onClick = { /*TODO*/ },
                                //colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryLight, disabledContentColor = primaryLight)
                            ) {
                                Text("Mam zaujem")
                            }
                            Button(modifier = Modifier.padding(end = 10.dp),
                                onClick = { /*TODO*/ },
                                //colors = ButtonDefaults.buttonColors(primaryLight)
                            ) {
                                Text(text = "Pridem")
                            }
                        }
                        Button(
                            onClick = { navController.navigate(Screens.EDIT_EVENT.name) },
                            //colors = ButtonDefaults.buttonColors(primaryLight)
                        ) {
                            Text(
                                text = "Upravit",
                                //fontSize = MaterialTheme.typography.bodySmall.fontSize
                            )
                        }
                    }
                }


                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(
                        onClick = {
                            navController.navigate(Screens.USER_PROFILE.name)
                        }
                    )
                ) {
                    Image(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            ,
                        painter = painterResource(id = profilePic),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = autor,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        //fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}


//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(navController = NavController())
//}


