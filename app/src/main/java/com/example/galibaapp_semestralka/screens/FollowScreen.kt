package com.example.galibaapp_semestralka.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.navigation.Screens


@Composable
fun FollowScreen(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel
) {

    LaunchedEffect(Unit) {

        firebaseViewModel.getMyFollowingList()

    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column {

            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Text(
                        modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp),
                        text = "Sleduješ",
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        //color = MaterialTheme.colorScheme.onSurface
                    )
                    IconButton(
                        modifier = Modifier.padding(end = 20.dp),
                        onClick = { }) {

                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search artists",
                        )
                    }
                }
            }

            val userList = firebaseViewModel.myFollowedUsers

            Box {

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {

                    for (user in userList) {
                        //Spacer(modifier = Modifier.height(15.dp))

                        var username by remember { mutableStateOf("") }
                        var bio by remember { mutableStateOf("") }

                        firebaseViewModel.getUserData(
                            usedId = user.toString(),
                            onSuccess = {
                                username = it.username.toString()
                                bio = it.bio.toString()
                            },
                            onFailure = {

                            }
                        )

                        CustomListItem(
                            navController,
                            firebaseViewModel,
                            userId = user.toString(),
                            meno = username,
                            popis = bio,
                            profilePic = R.drawable.backonlabelpfp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomListItem(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel,
    userId: String,
    meno: String,
    popis: String = "",
    @DrawableRes
    profilePic: Int
) {
    //TODO - spytaj sa jak mam spravit podmienku aby sa tam bud pridal alebo nepridal parameter
    Box(Modifier.clickable {
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
                userId.toString()
            )

    }) {

        var showDialog by remember { mutableStateOf(false) }
        val checkedState = remember { mutableStateOf(true) }


        val onUnfollowSuccess = {
            firebaseViewModel.isFollowing.value = false
            showDialog = false
            firebaseViewModel.getMyFollowingList()

        }

        val onUnfollowFailure: ()-> Unit = {
        }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Zrušiť sledovanie") },
                    text = { Text(text = "Naozaj chcete zrušiť sledovanie tohto používateľa?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                checkedState.value = false
                                firebaseViewModel.unFollow(onUnfollowSuccess, onUnfollowFailure, userId.toString())

                            }
                        ) {
                            Text(text = "Áno")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog = false }
                        ) {
                            Text(text = "Nie")
                        }
                    }
                )
            }

        ListItem(
            //colors = ListItemDefaults.colors(surfaceLight),
            headlineContent = { Text("@$meno") },
            supportingContent = { Text(text = popis) },
            trailingContent = {

                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        //checkedState.value = it
                        showDialog = true
                                      },

                )
            },

            leadingContent = {
                Image(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = profilePic),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        )
    }
    HorizontalDivider()
}

@Composable
fun CustomListItemCard(
    meno: String,
    popis: String = "",
    @DrawableRes
    profilePic: Int
) {
    Card(

    ) {
        Text(
            text = meno,
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            textAlign = TextAlign.Center,
        )
    }
}


//@Preview()
//@Composable
//fun FollowScreenPreview() {
//    FollowScreen()
//}