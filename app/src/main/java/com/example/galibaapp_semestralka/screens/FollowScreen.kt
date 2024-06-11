package com.example.galibaapp_semestralka.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.navigation.Screens


@Composable
fun FollowScreen(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel,
    modifier: Modifier

) {

    LaunchedEffect(Unit) {

        firebaseViewModel.getMyFollowingList()

    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier
        ) {

            Text(
                modifier = Modifier.padding(start = 20.dp, bottom = 20.dp),
                text = stringResource(R.string.title_following),
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            )


            val userList by firebaseViewModel.myFollowedUsers.collectAsState()

            Box {
                if (userList.isNotEmpty()) {

                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        for (user in userList) {

                            var username by remember { mutableStateOf("") }
                            var bio by remember { mutableStateOf("") }
                            var profilePic by remember { mutableStateOf("") }

                            firebaseViewModel.getUserData(
                                usedId = user.toString(),
                                onSuccess = {
                                    username = it.username.toString()
                                    bio = it.bio.toString()
                                    profilePic = it.profilePic.toString()
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
                                profilePic = profilePic
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(25.dp),
                        contentAlignment = Alignment.Center

                    ) {
                        Text(
                            text = stringResource(R.string.text_its_quet_here_with_following),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 40.sp
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
    profilePic: String?
) {
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


        val onUnfollowSuccess: ()-> Unit = {
            firebaseViewModel.isFollowing.value = false
            showDialog = false
            firebaseViewModel.getMyFollowingList()

        }

        val onUnfollowFailure: () -> Unit = {
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = stringResource(R.string.alert_unfollow)) },
                text = { Text(text = stringResource(R.string.alert_are_you_really_sure_you_want_unfollow)) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            checkedState.value = false
                            firebaseViewModel.unFollow(
                                onUnfollowSuccess,
                                onUnfollowFailure,
                                userId.toString()
                            )

                        }
                    ) {
                        Text(stringResource(R.string.button_confirm))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text(stringResource(R.string.button_cancel))
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
                if (profilePic?.isEmpty() == true) {
                    Image(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.empty_profile),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                } else {
                    AsyncImage(
                        model = profilePic,
                        contentDescription = null,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

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