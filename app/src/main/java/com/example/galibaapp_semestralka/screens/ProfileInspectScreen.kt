package com.example.galibaapp_semestralka.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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

    Log.d("profilovkaaaaaaa", "profile pic : ${chosenUser.value?.profilePic.toString()}")

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
                text = if (chosenUser.value?.isArtist == true) "Umelec/Kapela" else "Posluchac",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            Text(
                text = ("@" + (chosenUser.value?.username ?: "")),
                fontSize = MaterialTheme.typography.displayMedium.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )


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
                        socialMediaName = "Instagram",
                        socialMediaIcon = R.drawable.instagram_icon
                    )
                }

                if (chosenUser.value?.facebookUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.facebook.com/",
                        text = chosenUser.value?.facebookUsername.toString(),
                        socialMediaName = "Facebook",
                        socialMediaIcon = R.drawable.facebook_icon
                    )
                }

                if (chosenUser.value?.youtubeUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.youtube.com/@",
                        text = chosenUser.value?.youtubeUsername.toString(),
                        socialMediaName = "Youtube",
                        socialMediaIcon = R.drawable.youtube_icon
                    )
                }
                if (chosenUser.value?.tiktokUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.tiktok.com/@",
                        text = chosenUser.value?.tiktokUsername.toString(),
                        socialMediaName = "Tiktok",
                        socialMediaIcon = R.drawable.tiktok_icon
                    )
                }
                if (chosenUser.value?.website != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = chosenUser.value?.website.toString(),
                        socialMediaName = "Stranka",
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
                    Text(text = "Sledovane")
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = "Zrušiť sledovanie") },
                        text = { Text(text = "Naozaj chcete zrušiť sledovanie tohto používateľa?") },
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
                    Text(text = "Sledovat")
                }
            }

            Spacer(modifier = Modifier.padding(all = 10.dp))

            val eventList = firebaseViewModel.allEventsByUser


            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
            ) {


                for (event in eventList.value) {
                    //Spacer(modifier = Modifier.height(15.dp))

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

@Composable
fun ProfileInfo(
    @DrawableRes profilePic: Int = R.drawable.empty_profile,
    isArtist: Boolean,
    name: String,
    bio: String = ""

) {

    Image(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape),
        painter = painterResource(id = profilePic),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
    Spacer(modifier = Modifier.padding(all = 10.dp))

    Text(
        text = "Umelec/Kapela",
        fontSize = MaterialTheme.typography.bodyLarge.fontSize
    )

    Spacer(modifier = Modifier.padding(all = 10.dp))

    Text(
        text = name,
        fontSize = MaterialTheme.typography.displayMedium.fontSize,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.padding(all = 10.dp))

    Text(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        text = bio,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize
    )
    Spacer(modifier = Modifier.padding(all = 10.dp))

    HorizontalDivider()
}

//@Composable
//@Preview
//fun PreviewProfileInspectScreen() {
//    ProfileInspectScreen("id")
//}

