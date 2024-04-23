@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.galibaapp_semestralka.screens

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.galibaapp_semestralka.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(contentAlignment = androidx.compose.ui.Alignment.TopStart) {
            Column(
                modifier = Modifier.padding(10.dp, 10.dp)
            )
            {
                Text(
                    modifier = Modifier.padding(vertical = 30.dp),
                    text = "Ahoj, Šimon!",
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row {
                    Text(
                        text = "Banska Bystrica, Slovensko",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        modifier = Modifier.padding(start = 10.dp),
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = ""
                    )
                }
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

                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Galiby v okoli:",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Column (modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),

                    //verticalArrangement = Arrangement.spacedBy(26.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    CustomCard (
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
                    Spacer(modifier = Modifier.height(15.dp))

                    CustomCard (
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

@Composable
fun CustomCard(
        //modifier: Modifier = Modifier,
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
        modifier = Modifier.animateContentSize().clickable {
            showFullContent = !showFullContent
        },
        shape = RoundedCornerShape(16.dp),
        //colors = MaterialTheme.colorScheme.backgroundz,
        border = BorderStroke(1.dp, Color.Black)
    ) {


//        Surface(
//            modifier = Modifier.clickable {
//                showFullContent = !showFullContent
//            },
//            shape = RoundedCornerShape(16.dp),
//            color = MaterialTheme.colorScheme.primaryContainer,
//            border = BorderStroke(1.dp, Color.Black)
//        ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.wrapContentSize(),
                    //color = Color(0xFFD1D5E1)
                ) {
//                                    Text(
//                                        text = "New release",
//                                        fontSize =  12.sp,
//                                        style = MaterialTheme.typography.titleLarge,
//                                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//                                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = title,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(text = location)

                Spacer(modifier = Modifier.height(2.dp))

                Text(text = date)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(end = 5.dp),
                    text = text,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (showFullContent) 100 else 2
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (!showFullContent) {
                    Text(
                        text = "Read More",
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge
                    )
                } else {
                    Row {
                        OutlinedButton(
                            modifier = Modifier.padding(end = 10.dp),
                            onClick = { }) {
                            Text("Mam zaujem")
                        }
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Pridem")
                        }
                    }
                }


                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape),
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

            Surface(

                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp)
            ) {
                Image(

                    painter = painterResource(id = image),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}


