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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.data.Login.LoginViewModel
import com.example.galibaapp_semestralka.navigation.Screens
import com.google.accompanist.flowlayout.FlowRow


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState",
    "StateFlowValueCalledInComposition"
)
@Composable
fun UserScreen(
    navController: NavHostController, loginViewModel: LoginViewModel = viewModel(), firebaseViewModel: FirebaseViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val username by firebaseViewModel.username.observeAsState()
    val email by firebaseViewModel.emailId.observeAsState()
    val bio by firebaseViewModel.bio.observeAsState()
    val isArtist by firebaseViewModel.isArtist.observeAsState()
    val profilePic by firebaseViewModel.profilePic.observeAsState()
    val instagramUsername by firebaseViewModel.instagramUsername.observeAsState()
    val facebookUsername by firebaseViewModel.facebookUsername.observeAsState()
    val youtubeUsername by firebaseViewModel.youtubeUsername.observeAsState()
    val tiktokUsername by firebaseViewModel.tiktokUsername.observeAsState()
    val website by firebaseViewModel.website.observeAsState()

    val myEventList = firebaseViewModel.allEventsByUser.collectAsState()
    val interestedEventList = firebaseViewModel.myInterestedEvents.collectAsState()
    val comingEventList = firebaseViewModel.myComingEvents.collectAsState()

    var selectedCreated by rememberSaveable { mutableStateOf(true) }
    var selectedInterested by rememberSaveable { mutableStateOf(true) }
    var selectedComing by rememberSaveable { mutableStateOf(true) }

    //val eventList = firebaseViewModel.events

    LaunchedEffect(Unit) {
        firebaseViewModel.getCurrentUserData()
        firebaseViewModel.getAllEventsCreated(byUserId = firebaseViewModel.currentUserId.value.toString())
        firebaseViewModel.getMyInterestedEvents()
        firebaseViewModel.getMyComingEvents()
    }

    Surface (
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ){

        Column (
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
                IconButton(onClick = {
                    navController.navigate(Screens.EDIT_USER_PROFILE.name) {
                        launchSingleTop = true
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "editProfileIcon"
                    )
                }
            }
            Spacer(modifier = Modifier.padding(all = 20.dp))
            if (profilePic?.toString() == "null") {
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
                    model = profilePic,
                    contentDescription =null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }


            Spacer(modifier = Modifier.padding(all = 10.dp))

            Text(
                text = if (isArtist == true) stringResource(R.string.text_artist) else stringResource(R.string.text_listener),
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
                        text = username ?: "",
                        fontSize = MaterialTheme.typography.displaySmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 40.sp
                    )
                }
            }

            Spacer(modifier = Modifier.padding(all = 10.dp))

            Text(
                text = email ?: "",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            Spacer(modifier = Modifier.padding(all = 10.dp))
            Text(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                text = bio ?: "",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )

            FlowRow {
                if (instagramUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.instagram.com/",
                        text = instagramUsername.toString(),
                        socialMediaName = stringResource(R.string.text_instagram),
                        socialMediaIcon = R.drawable.instagram_icon
                    )
                    Spacer(modifier = Modifier.padding(all = 10.dp))

                }

                if (facebookUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.facebook.com/",
                        text = facebookUsername.toString(),
                        socialMediaName = stringResource(R.string.text_facebook),
                        socialMediaIcon = R.drawable.facebook_icon
                    )
                    Spacer(modifier = Modifier.padding(all = 10.dp))

                }

                if (youtubeUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.youtube.com/@",
                        text = youtubeUsername.toString(),
                        socialMediaName = stringResource(R.string.text_youtube),
                        socialMediaIcon = R.drawable.youtube_icon
                    )
                    Spacer(modifier = Modifier.padding(all = 10.dp))

                }
                if (tiktokUsername != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = "https://www.tiktok.com/@",
                        text = tiktokUsername.toString(),
                        socialMediaName = stringResource(R.string.text_tiktok),
                        socialMediaIcon = R.drawable.tiktok_icon
                    )
                    Spacer(modifier = Modifier.padding(all = 10.dp))

                }
                if (website != "") {
                    ClickableSocialMediaChip(
                        urlPrefix = website.toString(),
                        socialMediaName = stringResource(R.string.text_webpage),
                        socialMediaIcon = R.drawable.web_icon_default
                    )
                }

            }

            Spacer(modifier = Modifier.padding(all = 10.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "tvojeEventyIcon"
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = stringResource(R.string.text_your_events))
            }

            Spacer(modifier = Modifier.height(10.dp))



            FlowRow {
                if (myEventList.value.isNotEmpty()) {
                    FilterChip(
                        onClick = { selectedCreated = !selectedCreated },
                        label = { Text(stringResource(R.string.chip_created_events)) },
                        selected = selectedCreated,
                        leadingIcon = if (selectedCreated) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        },
                    )
                    Spacer(modifier = Modifier.padding(all = 5.dp))

                }

                FilterChip(
                    onClick = { selectedInterested = !selectedInterested },
                    label = { Text(stringResource(R.string.chip_is_interested)) },
                    selected = selectedInterested,
                    leadingIcon = if (selectedInterested) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
                Spacer(modifier = Modifier.padding(all = 5.dp))

                FilterChip(
                    onClick = { selectedComing = !selectedComing },
                    label = { Text(stringResource(R.string.chip_coming)) },
                    selected = selectedComing,
                    leadingIcon = if (selectedComing) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
            }

            Spacer(modifier = Modifier.padding(all = 5.dp))

            HorizontalDivider()

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
            ) {


                if (selectedCreated) {
                    for (event in myEventList.value) {
                        //Spacer(modifier = Modifier.height(15.dp))

                        CustomCard(
                            firebaseViewModel = firebaseViewModel,
                            navController = navController,
                            event = event,
                        )
                    }
                }


                if (selectedInterested) {
                    for (event in interestedEventList.value) {

                        CustomCard(
                            firebaseViewModel = firebaseViewModel,
                            navController = navController,
                            event = event,
                        )
                    }
                }

                if (selectedComing) {
                    for (event in comingEventList.value) {
                        //Spacer(modifier = Modifier.height(15.dp))

                        CustomCard(
                            firebaseViewModel = firebaseViewModel,
                            navController = navController,
                            event = event,
                        )
                    }
                }

            }



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp),

                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(all = 10.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    val onSuccess = {
                        navController.popBackStack()
                        navController.popBackStack()
                        if (navController.currentBackStackEntry?.lifecycle?.currentState?.isAtLeast(
                                Lifecycle.State.CREATED) != false
                        ) {
                            navController.navigate(Screens.AUTHROOT.name)
                        }
                    }


                    val onFailure: () -> Unit = {

                    }

                    firebaseViewModel.logout(onSuccess, onFailure)
                }

                ) {
                    Text(text = stringResource(R.string.button_log_out))
                }
                Spacer(modifier = Modifier.padding(all = 10.dp))
                HorizontalDivider()
                //Spacer(modifier = Modifier.padding(all = 5.dp))


                Text(
                    text = stringResource(R.string.footer_creator_name),
                    fontSize = MaterialTheme.typography.labelMedium.fontSize
                )
            }
        }
        //}
        //)


    }
}

@Composable
fun ClickableSocialMediaChip(
    urlPrefix: String,
    text: String = "",
    socialMediaName: String,
    socialMediaIcon: Int = R.drawable.web_icon_default

) {
    var url = ""
    if (text == "") {
        url = "https://www.$urlPrefix"
    } else {
        url = urlPrefix + text

    }

    val uriHandler = LocalUriHandler.current

    var chipColor: Color = MaterialTheme.colorScheme.secondary

    if (socialMediaName == stringResource(R.string.text_instagram)) {
        chipColor = Color(0xFFC13584)
    } else if (socialMediaName == stringResource(R.string.text_facebook)) {
        chipColor = Color(0xFF1877F2)
    } else if (socialMediaName == stringResource(R.string.text_youtube)) {
        chipColor = Color(0xFFFF0000)
    } else if (socialMediaName == stringResource(R.string.text_tiktok)) {
        chipColor = Color(0xFFff0050)
    }

    SuggestionChip(
        colors = SuggestionChipDefaults.suggestionChipColors(iconContentColor = chipColor),
        onClick = { uriHandler.openUri(url) },
        label = {
            Text(socialMediaName)
        },
        icon = { Icon(painter = painterResource(id = socialMediaIcon), contentDescription = "socialMediaIcon", modifier = Modifier.size(20.dp))
        }
    )
}
