package com.example.galibaapp_semestralka.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.galibaapp_semestralka.data.LoginRegisterViewModel
import com.example.galibaapp_semestralka.data.UIevent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit,loginViewModel: LoginRegisterViewModel = viewModel()) {
    var login by rememberSaveable { mutableStateOf("") }
    var heslo by rememberSaveable { mutableStateOf("") }

    val isFieldsEmpty = login.isNotEmpty() && heslo.isNotEmpty()

    var loginBeenClicked by remember {
        mutableStateOf(false)
    }
    var passwordBeenClicked by remember {
        mutableStateOf(false)
    }

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


            val (loginFocusRequester, passwordFocusRequester) = remember { FocusRequester.createRefs() }

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(loginFocusRequester)
                    .width(250.dp),
                        value = login,
                onValueChange = {
                    login = it
                    loginViewModel.onLoginEvent(UIevent.loginChanged(it))
                    loginBeenClicked = true
                                },
                label = { Text("Login") },
                singleLine = true,
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "accBoxIcon")
                },
                prefix = {
                    Text(text = "@")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { passwordFocusRequester.requestFocus() }
                ),
                supportingText = {
                    //Text(text = "Take pouzivatelske meno uz existuje")
                    if (loginBeenClicked && login.isEmpty()) {
                        Text(text = "Povinny udaj!")
                    }
                },
                isError = loginBeenClicked && login.isEmpty()
            )

            Spacer(modifier = Modifier.padding(10.dp))

            val passwordVisible = remember {
                mutableStateOf(false)
            }

            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(passwordFocusRequester)
                    .width(250.dp),
                value = heslo,
                onValueChange = {
                    heslo = it
                    loginViewModel.onLoginEvent(UIevent.passwordChanged(it))
                    passwordBeenClicked = true
                },
                label = { Text("Heslo") },
                singleLine = true,
                trailingIcon = {
                    val iconImage = if (heslo.isEmpty()) {
                        Icons.Default.Lock
                    } else if (passwordVisible.value && heslo.isNotEmpty()) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                    IconButton(onClick = {
                        passwordVisible.value = !passwordVisible.value
                    }) {
                        Icon(imageVector = iconImage, contentDescription ="" )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done

                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        ///navController.navigate(Screens.HOME.name)


                        if (isFieldsEmpty) {
                            onLoginClick()
                        }
                    }
                ),
                supportingText = {
                    //Text(text = "Pouzivatelske meno alebo heslo je nespravne")
                    if (passwordBeenClicked && heslo.isEmpty()) {
                        Text(text = "Povinny udaj!")
                    }
                },
                isError = passwordBeenClicked && heslo.isEmpty(),
                visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()

            )

            TextButton(onClick = onRegisterClick) {
                Text(text = "Nemas ucet ?")
            }

            Button(
                onClick = {
                    loginViewModel.onLoginEvent(UIevent.LoginButtonClicked)
                    onLoginClick() },
                enabled = isFieldsEmpty
            ) {
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