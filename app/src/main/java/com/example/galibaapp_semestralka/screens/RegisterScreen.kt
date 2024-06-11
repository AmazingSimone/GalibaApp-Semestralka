package com.example.galibaapp_semestralka.screens

//import RegisterViewModel
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.galibaapp_semestralka.data.Register.RegisterUIevent
import com.example.galibaapp_semestralka.data.Register.RegisterViewModel

@ExperimentalMaterial3Api
@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel(),
    firebaseViewModel: FirebaseViewModel
) {

    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var heslo by rememberSaveable { mutableStateOf("") }
    var heslo2 by rememberSaveable { mutableStateOf("") }


    Box(modifier = Modifier.fillMaxSize()) {


        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(

                            text = stringResource(R.string.title_register),
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        val (loginFocusRequester, emailFocusRequester, passwordFocusRequester, password2FocusRequester) = remember { FocusRequester.createRefs() }


                        OutlinedTextField(
                            modifier = Modifier
                                .focusRequester(loginFocusRequester)
                                .width(250.dp),
                            value = username,
                            onValueChange = {
                                username = it
                                registerViewModel.onRegisterEvent(RegisterUIevent.usernameChanged(it))
                                //loginBeenClicked = true
                            },
                            label = { Text(stringResource(R.string.text_username)) },
                            singleLine = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "accBoxIcon"
                                )
                            },


                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    emailFocusRequester.requestFocus()
                                }
                            ),
                            supportingText = {
                                if (registerViewModel.registrationUIState.value.usernameIsEmpty) {
                                    Text(text = stringResource(R.string.error_required_field))
                                } else if (registerViewModel.registrationUIState.value.usernameErr) {
                                    Text(text = stringResource(R.string.error_max_limit_reached))
                                }
                            },

                            isError = registerViewModel.registrationUIState.value.usernameIsEmpty || registerViewModel.registrationUIState.value.usernameErr    //loginBeenClicked && login.isEmpty()
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .focusRequester(emailFocusRequester)
                                .width(250.dp),
                            value = email,
                            onValueChange = {
                                email = it
                                registerViewModel.onRegisterEvent(RegisterUIevent.emailChanged(it))
                                //emailBeenClicked = true
                            },
                            label = { Text(stringResource(R.string.text_email)) },
                            singleLine = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Mail,
                                    contentDescription = "mailBoxIcon"
                                )
                            },

                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    passwordFocusRequester.requestFocus()
                                }
                            ),
                            supportingText = {
                                if (registerViewModel.registrationUIState.value.emailErr) {
                                    Text(text = stringResource(R.string.error_email_must_be_valid))
                                }
                            },

                            isError = registerViewModel.registrationUIState.value.emailErr //emailBeenClicked && email.isEmpty()
                        )

                        Spacer(modifier = Modifier.padding(10.dp))

                        val passwordVisible = rememberSaveable {
                            mutableStateOf(false)
                        }

                        OutlinedTextField(
                            modifier = Modifier
                                .focusRequester(passwordFocusRequester)
                                .width(250.dp),
                            value = heslo,
                            onValueChange = {
                                heslo = it
                                registerViewModel.onRegisterEvent(RegisterUIevent.passwordChanged(it))
                                //passwordBeenClicked = true
                            },
                            label = { Text(stringResource(R.string.text_password)) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { password2FocusRequester.requestFocus() }
                            ),
                            trailingIcon = {
                                //Icon(imageVector = Icons.Default.Lock, contentDescription = "passIcon")

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
                            supportingText = {
                                //Text(text = "Take pouzivatelske meno uz existuje")
                                if (registerViewModel.registrationUIState.value.passwordErr) {
                                    Text(text = stringResource(R.string.error_password_must_be_long_enough))
                                }
                            },
                            isError = registerViewModel.registrationUIState.value.passwordErr,  //passwordBeenClicked && ((password2BeenClicked && isPasswordSame) || heslo.isEmpty()),
                            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()

                        )

                        Spacer(modifier = Modifier.padding(10.dp))


                        val confirmPasswordVisible = rememberSaveable {
                            mutableStateOf(false)
                        }
                        val localFocusManager = LocalFocusManager.current
                        OutlinedTextField(
                            modifier = Modifier
                                .focusRequester(password2FocusRequester)
                                .width(250.dp),
                            value = heslo2,
                            onValueChange = {
                                heslo2 = it
                                registerViewModel.onRegisterEvent(
                                    RegisterUIevent.confirmPasswordChanged(
                                        it
                                    )
                                )
                                //password2BeenClicked = true
                            },
                            label = { Text(stringResource(R.string.text_confirm_password)) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions {
                                localFocusManager.clearFocus()
                            },
                            trailingIcon = {
                                val iconImage = if (heslo2.isEmpty()) {
                                    Icons.Default.Lock
                                } else if (confirmPasswordVisible.value && heslo2.isNotEmpty()) {
                                    Icons.Filled.Visibility
                                } else {
                                    Icons.Filled.VisibilityOff
                                }
                                IconButton(onClick = {
                                    confirmPasswordVisible.value = !confirmPasswordVisible.value
                                }) {
                                    Icon(imageVector = iconImage, contentDescription = "")
                                }
                            },
                            supportingText = {
                                if (registerViewModel.registrationUIState.value.passwordMatchErr) {
                                    Text(text = stringResource(R.string.error_passwords_arent_matching))
                                }
                            },
                            isError = registerViewModel.registrationUIState.value.passwordMatchErr, //password2BeenClicked && ((passwordBeenClicked && isPasswordSame) || heslo2.isEmpty()),
                            visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()

                        )

                        val options = mutableStateListOf<String>(
                            stringResource(R.string.text_listener),
                            stringResource(R.string.text_artist)
                        )
                        var selectedIndex by rememberSaveable {
                            mutableStateOf(0)
                        }

                        Spacer(modifier = Modifier.padding(10.dp))


                        SingleChoiceSegmentedButtonRow {

                            options.forEachIndexed { index, option ->
                                SegmentedButton(
                                    selected = selectedIndex == index,
                                    onClick = {
                                        selectedIndex = index
                                        if (selectedIndex == 0) {
                                            registerViewModel.onRegisterEvent(
                                                RegisterUIevent.isArtistChanged(
                                                    false
                                                )
                                            )
                                        } else {
                                            registerViewModel.onRegisterEvent(
                                                RegisterUIevent.isArtistChanged(
                                                    true
                                                )
                                            )
                                        }
                                    },
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = options.size
                                    )
                                )
                                {
                                    Text(text = option)
                                }
                            }

                        }

                        Spacer(modifier = Modifier.padding(10.dp))


                        TextButton(
                            onClick = {
                                onLoginClick()
                                username = ""
                                registerViewModel.registrationUIState.value.usernameErr = false
                                registerViewModel.registrationUIState.value.usernameIsEmpty = false
                                email = ""
                                registerViewModel.registrationUIState.value.emailErr = false
                                heslo = ""
                                registerViewModel.registrationUIState.value.passwordErr = false
                                registerViewModel.registrationUIState.value.passwordMatchErr = false
                                heslo2 = ""
                            }

                        ) {
                            Text(text = stringResource(R.string.text_already_have_account))
                        }


                        Spacer(modifier = Modifier.padding(10.dp))

                        val context = LocalContext.current
                        Button(
                            onClick = {
                                if (!registerViewModel.isAnyUserInputError()) {

                                    val onSuccess = {

                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.toast_account_was_created),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        onRegisterClick()
                                    }

                                    val onFailure: () -> Unit = {
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.toast_there_was_error_with_registration),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                    firebaseViewModel.signUp(
                                        onSuccess,
                                        onFailure,
                                        registerViewModel
                                    )
                                }

                            },
                            enabled =
                            !(registerViewModel.registrationUIState.value.usernameErr ||
                                    registerViewModel.registrationUIState.value.usernameIsEmpty ||
                                    registerViewModel.registrationUIState.value.emailErr ||
                                    registerViewModel.registrationUIState.value.passwordErr ||
                                    registerViewModel.registrationUIState.value.passwordMatchErr)
                        ) {
                            Text(text = stringResource(R.string.text_register))

                        }
                    }
                }
            )
        }
        if (registerViewModel.registerInProgress.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

//@Preview
//@Composable
//fun LoginPreview() {
//    RegisterScreen(navController = )
//}