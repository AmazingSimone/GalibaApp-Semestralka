package com.example.galibaapp_semestralka.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

    data class Data(
        var username: String? = "",
        var bio: String? = "",
        var isArtist: Boolean? = false
    )

    val username by firebaseViewModel.username.observeAsState()
    val bio by firebaseViewModel.bio.observeAsState()
    val isArtist by firebaseViewModel.isArtist.observeAsState()

    LaunchedEffect(Unit) {
        firebaseViewModel.getUserData()
    }

    Log.d("EditUserInfo", "$username , $bio, $isArtist")

    Surface {
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
                    text = "Uprav profil",
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

            Surface(
            ) {
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.empty_profile),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.padding(all = 10.dp))

            val usernameChanged = remember { mutableStateOf(username) }

            val (usernameFocusRequester, realNameFocusRequester, realSurnameFocusRequester, userEmailFocusRequester, userBioFocusRequester) = remember { FocusRequester.createRefs() }
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

            var userBioChanged = remember { mutableStateOf(bio) }

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
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    }
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
                        Button(
                            onClick = {
                                showDialog = false
//                                navController.navigate(Screens.AUTHROOT.name) {
//                                    popUpTo(Screens.HOME.name) { inclusive = true }
//                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Vymazat ucet")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDialog = false },

                        ) {
                            Text("Zrušiť")
                        }
                    },
                    title = { Text("Upozornenie") },
                    text = { Text("Si si isty? Ucet sa natrvalo vymaze.") }
                )
            }

            val context = LocalContext.current
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


                    val data = Data(usernameChanged.value,userBioChanged.value,isArtistChanged.value)

                    val onSuccess = {
                        navController.popBackStack()
                        Toast.makeText(context, "Zmeny boli ulozene", Toast.LENGTH_LONG).show()

                    }

                    val onFailure: () -> Unit = {
                        Toast.makeText(context, "Nastala chyba", Toast.LENGTH_LONG).show()
                    }
                    firebaseViewModel.updateData(onSuccess,onFailure,usernameChanged.value,userBioChanged.value,isArtistChanged.value)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = (usernameChanged.value?.isNotEmpty() ?: false && (username != usernameChanged.value || bio != userBioChanged.value || isArtist != isArtistChanged.value))
            ) {
                Text(text = "Ulozit zmeny")
            }
        }
    }
}
