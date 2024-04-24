package com.example.galibaapp_semestralka.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.galibaapp_semestralka.navigation.Screens

@Composable
fun LoginScreen(navController: NavController) {

    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {

        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(

                text = "Vitaj Spat !",
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(10.dp))

            var login by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Login") }
            )

            Spacer(modifier = Modifier.padding(10.dp))
            var heslo by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                value = heslo,
                onValueChange = { heslo = it },
                label = { Text("Heslo") }
            )

            TextButton(onClick = {
                navController.navigate(Screens.REGISTER.name)
            }) {
                Text(text = "Nemas ucet ?")
            }

            Button(onClick = {

                navController.navigate(Screens.HOME.name)
            }) {
                Text(text = "Prihlasenie")
            }
        }

    }

}

//@Preview
//@Composable
//fun RegisterPreview() {
//    LoginScreen()
//}