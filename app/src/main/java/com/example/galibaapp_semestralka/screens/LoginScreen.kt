package com.example.galibaapp_semestralka.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.galibaapp_semestralka.R
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.data.Login.LoginUIevent
import com.example.galibaapp_semestralka.data.Login.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(),
    firebaseViewModel: FirebaseViewModel
) {

    var email by rememberSaveable { mutableStateOf("") }
    var heslo by rememberSaveable { mutableStateOf("") }


    Box {
        Surface(
            modifier = Modifier

                .background(MaterialTheme.colorScheme.background)
        ) {

            val snackbarHostState = remember { SnackbarHostState() }
            //val scope = rememberCoroutineScope()

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                content = {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = stringResource(R.string.title_login),
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.padding(10.dp))


                        val (loginFocusRequester, passwordFocusRequester) = remember { FocusRequester.createRefs() }

                        OutlinedTextField(
                            modifier = Modifier
                                .focusRequester(loginFocusRequester)
                                .width(250.dp),
                            value = email,
                            onValueChange = {
                                email = it
                                loginViewModel.onLoginEvent(LoginUIevent.emailChanged(it))
                            },
                            label = { Text(stringResource(R.string.text_email)) },
                            singleLine = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "accBoxIcon"
                                )
                            },

                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { passwordFocusRequester.requestFocus() }
                            ),
                            supportingText = {
                                if (loginViewModel.loginUIState.value.emailErr) {
                                    Text(text = stringResource(R.string.error_email_must_be_valid))
                                }
                            },
                            isError = loginViewModel.loginUIState.value.emailErr || loginViewModel.badLogin.value
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        val passwordVisible = remember {
                            mutableStateOf(false)
                        }
                        val localFocusManager = LocalFocusManager.current
                        OutlinedTextField(
                            modifier = Modifier
                                .focusRequester(passwordFocusRequester)
                                .width(250.dp),
                            value = heslo,
                            onValueChange = {
                                heslo = it
                                loginViewModel.onLoginEvent(LoginUIevent.passwordChanged(it))
                                //passwordBeenClicked = true
                            },
                            label = { Text(stringResource(R.string.text_password)) },
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
                                    Icon(imageVector = iconImage, contentDescription = "")
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done

                            ),
                            keyboardActions = KeyboardActions {
                                localFocusManager.clearFocus()
                            },
                            supportingText = {
                                //Text(text = "Pouzivatelske meno alebo heslo je nespravne")
                                if (loginViewModel.loginUIState.value.passwordErr) {
                                    Text(text = stringResource(R.string.error_required_field))
                                }
                            },
                            isError = loginViewModel.loginUIState.value.passwordErr || loginViewModel.badLogin.value,
                            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()

                        )

                        TextButton(
                            onClick = {
                                onRegisterClick()
                                email = ""
                                heslo = ""

                                loginViewModel.loginUIState.value.emailErr = false
                                loginViewModel.loginUIState.value.passwordErr = false
                            }) {
                            Text(text = stringResource(R.string.text_dont_have_account))
                        }

                        val context = LocalContext.current
                        Button(
                            onClick = {
                                //loginViewModel.onLoginEvent(LoginUIevent.LoginButtonClicked)

                                if (!loginViewModel.isAnyUserInputError()) {
                                    val onSuccess = {
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.toast_welcome_back),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        onLoginClick()
                                    }

                                    val onFailure: () -> Unit = {
//                                        scope.launch {
//                                            snackbarHostState.showSnackbar(
//                                                message = "Nespravny login alebo heslo",
//                                                duration = SnackbarDuration.Short
//                                            )
//                                        }
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.toast_bad_login_or_password),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        loginViewModel.badLogin.value = true
                                    }
                                    firebaseViewModel.login(onSuccess, onFailure, loginViewModel)
                                }
                            },
                            enabled = !(
                                    loginViewModel.loginUIState.value.emailErr ||
                                            loginViewModel.loginUIState.value.passwordErr
                                    )

                        ) {
                            Text(text = stringResource(R.string.text_login))
                        }
                    }
                }
            )
        }
        if (loginViewModel.loginInProgress.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

//@Preview
//@Composable
//fun RegisterPreview() {
//    LoginScreen()
//}