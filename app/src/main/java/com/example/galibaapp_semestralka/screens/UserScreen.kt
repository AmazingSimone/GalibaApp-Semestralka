package com.example.galibaapp_semestralka.screens

import android.annotation.SuppressLint
import android.os. Build
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.data.Login.LoginViewModel
import com.example.galibaapp_semestralka.navigation.Screens
import com.example.galibaapp_semestralka.screens.HomeScreen.CustomCard
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch


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

    val eventList = firebaseViewModel.events.collectAsState()
    val interestedEventList = firebaseViewModel.myInterestedEvents.collectAsState()
    val comingEventList = firebaseViewModel.myComingEvents.collectAsState()

    var selectedCreated by remember { mutableStateOf(true) }
    var selectedInterested by remember { mutableStateOf(true) }
    var selectedComing by remember { mutableStateOf(true) }

    //val eventList = firebaseViewModel.events

    LaunchedEffect(Unit) {
        firebaseViewModel.getCurrentUserData()
        //firebaseViewModel.getMyCreatedEvents()
        firebaseViewModel.getAllEventsCreated(byUserId = firebaseViewModel.currentUserId.value.toString())
        firebaseViewModel.getMyInterestedEvents()
        firebaseViewModel.getMyComingEvents()
    }

    Surface {

        Scaffold (
            snackbarHost = {SnackbarHost(snackbarHostState)},
            content = {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 20.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
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

                    Image(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.empty_profile),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(all = 10.dp))

                    Text(
                        text = if (isArtist == true) "Umelec/Kapela" else "Posluchac",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )

                    Spacer(modifier = Modifier.padding(all = 10.dp))

                    Text(
                        text = ("@" + (username ?: "")),
                        fontSize = MaterialTheme.typography.displayMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )


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
                            Text(text = "Tvoje eventy")
                        }

                        Spacer(modifier = Modifier.height(10.dp))



                        FlowRow {
                            if (eventList.value.isNotEmpty()) {
                                FilterChip(
                                    onClick = { selectedCreated = !selectedCreated },
                                    label = { Text("Vytvorene") },
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
                            }

                            Spacer(modifier = Modifier.padding(all = 5.dp))

                            FilterChip(
                                onClick = { selectedInterested = !selectedInterested },
                                label = { Text("Mas zaujem") },
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
                                label = { Text("Ides") },
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
                    Spacer(modifier = Modifier.padding(all = 5.dp))

                    //Log.d("FirebaseViewModel", "${eventList.size}")

//                    LazyColumn {
//                        itemsIndexed(eventList) {
//                                index, item ->
//                            CustomCard(
//                                navController = navController,
//                                image = R.drawable.backonlabelpfp,
//                                title = eventList[index]?.nazovAkcie,
//                                location = eventList[index]?.miestoAkcie.toString(),
//                                date = eventList[index]?.datumACasAkcie?.toLocalDate().toString(),
//                                text = eventList[index]?.popisakcie.toString(),
//                                autor = username.toString(),
//                                profilePic = R.drawable.backonlabelpfp
//                            )
//                        }
//                    }




//                    Column (modifier = Modifier.padding(20.dp)) {
//                        for (event in eventList) {
//                            CustomCard(
//                                navController = navController,
//                                image = R.drawable.backonlabelpfp,
//                                title = event?.nazov.toString(),
//                                location = event?.miesto.toString(),
//                                date = "",
//                                text = event?.popis.toString(),
//                                autor = firebaseViewModel.username.toString(),
//                                profilePic = R.drawable.backonlabelpfp
//                            )
//                        }
//                    }

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                    ) {


//                        CustomCard(
//                            navController,
//                            image = R.drawable.backtooldschoolposter,
//                            title = "Back To Oldschool",
//                            location = "Klub 77, Banska Bystrica",
//                            date = "6.5.2023",
//                            text = "Throwback party pre mladých? Aj to je koncept. BackToOldschool sa vracia vo svojej siedmej edícií\uD83D\uDD7A\n" +
//                                    ".\n" +
//                                    "Hráme šialené mashupy trackov z rokov 60’ až 00’. Ako spolu znie Sara Perche Ti Amo a Du Hast? Alebo Rihanna a Meki Žbirka? ",
//                            autor = "Back On Label",
//                            profilePic = R.drawable.backonlabelpfp
//                        )

                        if (selectedCreated) {
                            for (event in eventList.value) {
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


                        if (selectedInterested) {
                            for (event in interestedEventList.value) {
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

                        if (selectedComing) {
                            for (event in comingEventList.value) {
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

                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    val onSuccess = {


                    }

                    val onFailure: () -> Unit = {

                    }








                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 10.dp),

                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {


                            navController.popBackStack()
                            navController.popBackStack()
                            if (navController.currentBackStackEntry?.lifecycle?.currentState?.isAtLeast(
                                    Lifecycle.State.CREATED) != false
                            ) {
                                navController.navigate(Screens.AUTHROOT.name)
                            }

                            val onFailure: () -> Unit = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Pri odhlasovani stala chyba",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }

                            firebaseViewModel.logout(onSuccess, onFailure)
                        }
                        ) {
                            Text(text = "Odhlasit sa")
                        }
                        Spacer(modifier = Modifier.padding(all = 10.dp))
                        HorizontalDivider()
                        //Spacer(modifier = Modifier.padding(all = 5.dp))


                        Text(
                            text = "Šimon Bartánus 2024",
                            fontSize = MaterialTheme.typography.labelMedium.fontSize
                        )
                    }
                }
            }
        )


    }
}

//@Composable
//@Preview
//fun PreviewUserSceen() {
//    UserScreen()
//}