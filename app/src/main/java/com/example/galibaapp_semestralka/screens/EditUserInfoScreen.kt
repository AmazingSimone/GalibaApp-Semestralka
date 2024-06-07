package com.example.galibaapp_semestralka.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.navigation.Screens

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditUserInfoScreen(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel
) {

//    val username = "amazingsimone"
//    val bio = "omg omg omg omg omg omg omg"
//    val isArtist = false


    val username by firebaseViewModel.username.observeAsState()
    val bio by firebaseViewModel.bio.observeAsState()
    val isArtist by firebaseViewModel.isArtist.observeAsState()
    val profilePic by firebaseViewModel.profilePic.observeAsState()
    val instagramUsername by firebaseViewModel.instagramUsername.observeAsState()
    val facebookUsername by firebaseViewModel.facebookUsername.observeAsState()
    val youtubeUsername by firebaseViewModel.youtubeUsername.observeAsState()
    val tiktokUsername by firebaseViewModel.tiktokUsername.observeAsState()
    val website by firebaseViewModel.website.observeAsState()


    val context = LocalContext.current
    val usernameChanged = remember { mutableStateOf(username) }

    var userBioChanged = remember { mutableStateOf(bio) }

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val instagramUsernameChanged = remember { mutableStateOf(instagramUsername) }

    val facebookUsernameChanged = remember { mutableStateOf(facebookUsername) }

    val youtubeUsernameChanged = remember { mutableStateOf(youtubeUsername) }

    val tiktokUsernameChanged = remember { mutableStateOf(tiktokUsername) }

    val websiteChanged = remember { mutableStateOf(website) }



    LaunchedEffect(Unit) {
        firebaseViewModel.getCurrentUserData()
    }

    Log.d("EditUserInfo", "$username , $bio, $isArtist")

    Surface (
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
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Uprav profil \uD83D\uDC85",
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize
                )
                IconButton(onClick = {
                    navController.navigate(Screens.PERSONAL_USER_PROFILE.name) {
                        popUpTo(Screens.HOME.name)
                    }
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "closeIcon")
                }
            }



            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {
                    selectedImageUri = it
                }
            )

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )

                    }
            ) {

                if (profilePic?.toString() == "null" && selectedImageUri == null) {
                    Image(
                        painter = painterResource(id = R.drawable.empty_profile),
                        contentDescription = "empty profile",
                        modifier = Modifier
                            .matchParentSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                } else if (selectedImageUri != null) {

                    AsyncImage(
                        model =selectedImageUri,
                        contentDescription =null,
                        modifier = Modifier
                            .matchParentSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    AsyncImage(
                        model = profilePic,
                        contentDescription =null,
                        modifier = Modifier
                            .matchParentSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                }



                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color(0x80000000))
                )

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.padding(all = 10.dp))


            val (usernameFocusRequester,
                userBioFocusRequester,
                usernameInstagramFocusRequester,
                usernameFacebookFocusRequester,
                usernameYoutubeFocusRequester,
                usernameTiktokFocusRequester,
                websiteFocusRequester
                ) = remember { FocusRequester.createRefs() }
            val localFocusManager = LocalFocusManager.current

            val maxChar = 15
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(usernameFocusRequester)
                    .fillMaxWidth(),
                value = usernameChanged.value.toString(),
                onValueChange = {
                    if (it.length <= maxChar) {
                        usernameChanged.value = it
                    }
                },
                label = { Text("Pouzivatelske meno") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                prefix = {
                    Text(text = "@")
                },
                keyboardActions = KeyboardActions(
                    onNext = { userBioFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))


            val maxCharBio = 150
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(userBioFocusRequester)
                    .fillMaxWidth(),
                value = userBioChanged.value.toString(),
                onValueChange = {
                    if (it.length <= maxCharBio) {
                        userBioChanged.value = it
                    }
                },
                label = { Text("Bio") },
                minLines = 5,
                maxLines = 5,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                    }
                )
            )
            Spacer(modifier = Modifier.padding(all = 10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(usernameInstagramFocusRequester)
                    .fillMaxWidth(),
                value = instagramUsernameChanged.value.toString(),
                onValueChange = {
                        instagramUsernameChanged.value = it
                },
                label = { Text("Instagram") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                prefix = {
                    Text(text = "@")
                },
                suffix = {
                         Icon(painter = painterResource(id = R.drawable.instagram_icon), contentDescription = "ig", modifier = Modifier.size(24.dp))
                },
                keyboardActions = KeyboardActions(
                    onNext = { usernameFacebookFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(usernameFacebookFocusRequester)
                    .fillMaxWidth(),
                value = facebookUsernameChanged.value.toString(),
                onValueChange = {
                    facebookUsernameChanged.value = it
                },
                label = { Text("Facebook") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                prefix = {
                    Text(text = "@")
                },
                suffix = {
                    Icon(painter = painterResource(id = R.drawable.facebook_icon), contentDescription = "fb", modifier = Modifier.size(24.dp))
                },
                keyboardActions = KeyboardActions(
                    onNext = { usernameYoutubeFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(usernameYoutubeFocusRequester)
                    .fillMaxWidth(),
                value = youtubeUsernameChanged.value.toString(),
                onValueChange = {
                    youtubeUsernameChanged.value = it
                },
                label = { Text("YouTube") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                prefix = {
                    Text(text = "@")
                },
                suffix = {
                    Icon(painter = painterResource(id = R.drawable.youtube_icon), contentDescription = "yt", modifier = Modifier.size(24.dp))
                },
                keyboardActions = KeyboardActions(
                    onNext = { usernameTiktokFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(usernameTiktokFocusRequester)
                    .fillMaxWidth(),
                value = tiktokUsernameChanged.value.toString(),
                onValueChange = {
                    tiktokUsernameChanged.value = it
                },
                label = { Text("TikTok") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                prefix = {
                    Text(text = "@")
                },
                suffix = {
                    Icon(painter = painterResource(id = R.drawable.tiktok_icon), contentDescription = "tt")
                },
                keyboardActions = KeyboardActions(
                    onNext = { websiteFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(websiteFocusRequester)
                    .fillMaxWidth(),
                value = websiteChanged.value.toString(),
                onValueChange = {
                    websiteChanged.value = it
                },
                label = { Text("Webstranka") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                suffix = {
                    Icon(painter = painterResource(id = R.drawable.web_icon_default), contentDescription = "fb")
                },
                keyboardActions = KeyboardActions(
                    onNext = { localFocusManager.clearFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            var options = mutableStateListOf("Posluchac", "Umelec")
            var selectedIndex by remember { mutableStateOf(if (isArtist == true) 1 else 0) }
            var isArtistChanged = remember { mutableStateOf(isArtist) }

            Spacer(modifier = Modifier.padding(10.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    options.forEachIndexed { index, option ->
                        SegmentedButton(
                            modifier = Modifier.weight(1f), // Ensures buttons take equal space
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                isArtistChanged.value = selectedIndex == 1
                            },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            )
                        ) {
                            Text(text = option)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            var showDialog by remember { mutableStateOf(false) }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                                navController.popBackStack()
                                navController.popBackStack()
                                navController.navigate(Screens.AUTHROOT.name)
                                val onSuccess: () -> Unit = {
                                    Log.d("vymazatUcet", "vymazalo")


                                }
                                val onFailure = {}

                                firebaseViewModel.deleteUser(onSuccess,onFailure,firebaseViewModel.currentUserId.value.toString())
                                //Log.d("userDelete", "id : ${firebaseViewModel.currentUserId.toString()}")
//                                navController.navigate(Screens.AUTHROOT.name) {
//                                    popUpTo(Screens.HOME.name) { inclusive = true }
//                                }
                            },
                            //colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text(
                                "Vymazat ucet",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog = false },

                        ) {
                            Text("Zrušiť")
                        }
                    },
                    title = { Text("Upozornenie") },
                    text = { Text("Si si isty? Ucet sa natrvalo vymaze.") }
                )
            }

                        OutlinedButton(
                onClick = {
                    showDialog = true

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Vymazat ucet", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.padding(10.dp))



            Button(
                onClick = {


                    //val data = Data(usernameChanged.value,userBioChanged.value,isArtistChanged.value)



                    val onSuccess = {

                        Toast.makeText(context, "Zmeny boli ulozene", Toast.LENGTH_LONG).show()
                        firebaseViewModel.getCurrentUserData()

                    }

                    val onFailure: () -> Unit = {
                        Toast.makeText(context, "Nastala chyba", Toast.LENGTH_LONG).show()
                    }
                    firebaseViewModel.updateUserData(
                        onSuccess,
                        onFailure,
                        usernameChanged.value,
                        userBioChanged.value,
                        isArtistChanged.value,
                        instagramUsernameChanged.value,
                        facebookUsernameChanged.value,
                        youtubeUsernameChanged.value,
                        tiktokUsernameChanged.value,
                        websiteChanged.value
                    )

                    if (selectedImageUri != null) {
                        val onSuccessUpload = {
                            Toast.makeText(context, "Obrazok bol nahraty", Toast.LENGTH_LONG).show()
                            selectedImageUri = null
                        }
                        val onFailureUpload = {
                            Toast.makeText(context, "Nastala chyba pri nahravani obrazku", Toast.LENGTH_LONG).show()

                        }
                        firebaseViewModel.saveToUserProfilePictures(onSuccessUpload,onFailureUpload,
                            selectedImageUri!!
                        )
                    }

                },

                modifier = Modifier.fillMaxWidth(),
                enabled = (usernameChanged.value?.isNotEmpty() ?: false && (username != usernameChanged.value ||
                        bio != userBioChanged.value ||
                        isArtist != isArtistChanged.value ||
                        selectedImageUri != null ||
                        instagramUsername != instagramUsernameChanged.value ||
                        facebookUsername != facebookUsernameChanged.value ||
                        youtubeUsername != youtubeUsernameChanged.value ||
                        tiktokUsername != tiktokUsernameChanged.value ||
                        website != websiteChanged.value
                        ))
            ) {
                Text(text = "Ulozit zmeny")
            }
            Log.d("neuveritelnyVyberacFotiek", "isArtist ${isArtist}")
            Log.d("neuveritelnyVyberacFotiek", "isArtistChanged ${isArtistChanged}")
        }
    }
}
