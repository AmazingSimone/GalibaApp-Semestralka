package com.example.galibaapp_semestralka.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.HomeViewModel
import com.example.galibaapp_semestralka.data.LoginViewModel
import com.example.galibaapp_semestralka.navigation.Screens
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserScreen(
    navController: NavHostController,loginViewModel: LoginViewModel = viewModel(),homeViewModel: HomeViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val username by homeViewModel.username.observeAsState()
    val isArtist by homeViewModel.isArtist.observeAsState()

    LaunchedEffect(Unit) {
        homeViewModel.getUserData()
    }
    Surface {

        Scaffold (
            snackbarHost = {SnackbarHost(snackbarHostState)},
            content = {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "iconClose")
                        }
                        IconButton(onClick = { navController.navigate(Screens.EDIT_USER_PROFILE.name) {
                            launchSingleTop = true
                        } }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "editProfileIcon")
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
                        text = homeViewModel.emailId.value?:"",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )

                }

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp),

                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Button(onClick = {


                        val onSuccess = {

                            navController.popBackStack()
                            navController.popBackStack()
                            navController.navigate(Screens.AUTHROOT.name)
//                            {
//                                popUpTo(Screens.REGISTER.name)
//                            }
                        }

                        val onFailure:() -> Unit = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Pri odhlasovani stala chyba",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }

                        loginViewModel.logout(onSuccess,onFailure)
                    }
                    ) {
                        Text(text = "Odhlasit sa")
                    }
                    Spacer(modifier = Modifier.padding(all = 10.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.padding(all = 5.dp))


                    Text(
                        text = "Šimon Bartánus 2024",
                        fontSize = MaterialTheme.typography.labelMedium.fontSize
                    )


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