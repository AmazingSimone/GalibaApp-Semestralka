package com.example.galibaapp_semestralka.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.google.accompanist.flowlayout.FlowRow

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun ProfileInspectScreen(
    firebaseViewModel: FirebaseViewModel,
    navController: NavController
) {
    val chosenUser = firebaseViewModel.chosenUser

    val isFollowing by firebaseViewModel.isFollowing.observeAsState()

    LaunchedEffect(Unit) {
        firebaseViewModel.getAllEventsCreated(byUserId = chosenUser.value?.userId.toString())
        firebaseViewModel.isFollowing(chosenUser.value?.userId.toString())
    }


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                //.padding(all = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "iconClose"
                    )
                }
            }

            Spacer(modifier = Modifier.padding(all = 20.dp))
            if (chosenUser.value?.profilePic?.isEmpty() == true) {
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.empty_profile),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            } else {
                AsyncImage(
                    model = chosenUser.value?.profilePic,
                    contentDescription =null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }


            Spacer(modifier = Modifier.padding(all = 10.dp))

            Text(
                text = if (chosenUser.value?.isArtist == true) stringResource(R.string.text_artist) else stringResource(
                    id = R.string.text_listener
                ),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            Row {
                Text(
                    text = "@",
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
                FlowRow {
                    Text(
                        text = chosenUser.value?.username ?: "",
                        fontSize = MaterialTheme.typography.displaySmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 40.sp
                    )
                }
            }


            Spacer(modifier = Modifier.padding(all = 10.dp))


            Spacer(modifier = Modifier.padding(all = 10.dp))
            Text(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                text = chosenUser.value?.bio ?: "",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )

            FlowRow {
                if (chosenUser.value?.instagramUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.instagram.com/",
                        text = chosenUser.value?.instagramUsername.toString(),
                        socialMediaName = stringResource(R.string.text_instagram),
                        socialMediaIcon = R.drawable.instagram_icon
                    )
                }

                if (chosenUser.value?.facebookUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.facebook.com/",
                        text = chosenUser.value?.facebookUsername.toString(),
                        socialMediaName = stringResource(R.string.text_facebook),
                        socialMediaIcon = R.drawable.facebook_icon
                    )
                }

                if (chosenUser.value?.youtubeUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.youtube.com/@",
                        text = chosenUser.value?.youtubeUsername.toString(),
                        socialMediaName = stringResource(R.string.text_youtube),
                        socialMediaIcon = R.drawable.youtube_icon
                    )
                }
                if (chosenUser.value?.tiktokUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.tiktok.com/@",
                        text = chosenUser.value?.tiktokUsername.toString(),
                        socialMediaName = stringResource(R.string.text_tiktok),
                        socialMediaIcon = R.drawable.tiktok_icon
                    )
                }
                if (chosenUser.value?.website != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = chosenUser.value?.website.toString(),
                        socialMediaName = stringResource(R.string.text_webpage),
                        socialMediaIcon = R.drawable.web_icon_default
                    )
                }
            }

            Spacer(modifier = Modifier.padding(all = 10.dp))


            var showDialog by remember { mutableStateOf(false) }

            val onFollowSuccess = {
                firebaseViewModel.isFollowing.value = true
            }

            val onFollowFailure = {
            }

            val onUnfollowSuccess = {
                firebaseViewModel.isFollowing.value = false
                showDialog = false
            }

            val onUnfollowFailure: () -> Unit = {
            }

            if (isFollowing == true) {

                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.button_following))
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = stringResource(R.string.alert_unfollow)) },
                        text = { Text(text = stringResource(R.string.alert_are_you_really_sure_you_want_unfollow)) },
                        confirmButton = {
                            TextButton(
                                onClick = {

                                    firebaseViewModel.unFollow(
                                        onUnfollowSuccess,
                                        onUnfollowFailure,
                                        chosenUser.value?.userId.toString()
                                    )

                                }
                            ) {
                                Text(text = stringResource(R.string.button_confirm))
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDialog = false }
                            ) {
                                Text(text = stringResource(R.string.button_cancel))
                            }
                        }
                    )
                }
            } else {
                OutlinedButton(
                    onClick = {

                        firebaseViewModel.giveFollow(
                            onFollowSuccess,
                            onFollowFailure,
                            chosenUser.value?.userId.toString()
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.button_follow))
                }
            }

            Spacer(modifier = Modifier.padding(all = 10.dp))

            val eventList = firebaseViewModel.allEventsByUser


            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
            ) {


                for (event in eventList.value) {

                    CustomCard(
                        firebaseViewModel = firebaseViewModel,
                        navController = navController,
                        event = event,
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

//@Composable
//@Preview
//fun PreviewProfileInspectScreen() {
//    ProfileInspectScreen("id")
//}

