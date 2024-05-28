package com.example.galibaapp_semestralka.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.navigation.Screens

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditUserInfoScreen(
    navController: NavController
) {
    Surface {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Row (
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
                IconButton(onClick = { navController.navigate(Screens.PERSONAL_USER_PROFILE.name) {
                    popUpTo(Screens.HOME.name)
                } }) {
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

            var userId by rememberSaveable { mutableStateOf("") }

            val (userIdFocusRequester, realNameFocusRequester, realSurnameFocusRequester, userEmailFocusRequester, userBioFocusRequester) = remember { FocusRequester.createRefs() }

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(userIdFocusRequester)
                    .fillMaxWidth(),
                value = userId,
                onValueChange = { userId = it },
                label = { Text("Pouzivatelske meno") },
                singleLine = true,
                readOnly = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                prefix = {
                    Text(text = "@")
                },
                keyboardActions = KeyboardActions(
                    onNext = { realNameFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            var userRealName by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(realNameFocusRequester)
                    .fillMaxWidth(),
                value = userRealName,
                onValueChange = { userRealName = it },
                label = { Text("Meno") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { realSurnameFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))

            var userRealSurname by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(realSurnameFocusRequester)
                    .fillMaxWidth(),
                value = userRealSurname,
                onValueChange = { userRealSurname = it },
                label = { Text("Priezvisko") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { userEmailFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.padding(all = 10.dp))



            var userEmail by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(userEmailFocusRequester)
                    .fillMaxWidth(),
                value = userEmail,
                onValueChange = { userEmail = it },
                label = { Text("Email") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { userBioFocusRequester.requestFocus() }
                )
            )
            Spacer(modifier = Modifier.padding(all = 10.dp))

            var userBio by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(realSurnameFocusRequester)
                    .fillMaxWidth(),
                value = userBio,
                onValueChange = { userBio = it },
                label = { Text("Bio") },
                minLines = 10,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {  }
                )
            )
            Spacer(modifier = Modifier.padding(all = 10.dp))

            Button(onClick = { navController.navigate(Screens.PERSONAL_USER_PROFILE.name) }) {
                Text(text = "Ulozit zmeny")
            }
        }
    }
}

//@Composable
//@Preview
//fun PreviewEditUserInfoScreen() {
//    EditUserInfoScreen()
//}