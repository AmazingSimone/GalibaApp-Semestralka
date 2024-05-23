package com.example.galibaapp_semestralka.screens

import android.annotation.SuppressLint
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {

    var login by rememberSaveable { mutableStateOf("") }
    var heslo by rememberSaveable { mutableStateOf("") }
    var heslo2 by rememberSaveable { mutableStateOf("") }

    var isPasswordSame by remember {
        mutableStateOf(false)
    }
    isPasswordSame = heslo != heslo2
    val isFieldsEmpty = login.isNotEmpty() && heslo.isNotEmpty() && heslo2.isNotEmpty()

    var loginBeenClicked by remember {
        mutableStateOf(false)
    }
    var passwordBeenClicked by remember {
        mutableStateOf(false)
    }
    var password2BeenClicked by remember {
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

                text = "Vitaj na palube !",
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(10.dp))

            val (loginFocusRequester, passwordFocusRequester, password2FocusRequester) = remember { FocusRequester.createRefs() }


            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(loginFocusRequester)
                    .width(250.dp),
                value = login,
                onValueChange = { login = it
                                loginBeenClicked = true
                                },
                label = { Text("Pouzivatelske Meno") },
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
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
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



            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(passwordFocusRequester)
                    .width(250.dp),
                value = heslo,
                onValueChange = {
                    heslo = it
                    passwordBeenClicked = true
                                },
                label = { Text("Heslo") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { password2FocusRequester.requestFocus() }
                ),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "passIcon")
                },
                supportingText = {
                    //Text(text = "Take pouzivatelske meno uz existuje")
                    if (passwordBeenClicked && heslo.isEmpty()) {
                        Text(text = "Povinny udaj!")
                    }
                },
                isError = passwordBeenClicked && ((password2BeenClicked && isPasswordSame) || heslo.isEmpty())

            )

            Spacer(modifier = Modifier.padding(10.dp))


            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(password2FocusRequester)
                    .width(250.dp),
                value = heslo2,
                onValueChange = {
                    heslo2 = it
                    password2BeenClicked = true
                                },
                label = { Text("Potvrd heslo") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        ///navController.navigate(Screens.HOME.name)
                        isPasswordSame = heslo != heslo2

                        if (!isPasswordSame && isFieldsEmpty) {
                            onRegisterClick()
                        }
                    }
                ),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "passIcon")
                },
                visualTransformation = PasswordVisualTransformation(),
                supportingText = {
                    if (password2BeenClicked && passwordBeenClicked && isPasswordSame) {
                        Text(text = "Hesla sa nezhoduju")
                    } else if (password2BeenClicked && heslo2.isEmpty()) {
                        Text(text = "Povinny udaj!")
                    }
                },
                isError = password2BeenClicked && ((passwordBeenClicked && isPasswordSame) || heslo2.isEmpty())
            )

            var options = mutableStateListOf<String>("Posluchac", "Umelec")
            var selectedIndex  by remember {
                mutableStateOf(0)
            }

            Spacer(modifier = Modifier.padding(10.dp))


            SingleChoiceSegmentedButtonRow{

                options.forEachIndexed { index, option ->
                    SegmentedButton(
                        selected = selectedIndex == index
                        , onClick = { selectedIndex = index }
                        , shape = SegmentedButtonDefaults.itemShape(
                            index = index
                            , count = options.size
                        )
                    )
                    {
                        Text(text = option)
                    }
                }

            }

            Spacer(modifier = Modifier.padding(10.dp))


            TextButton(onClick =
                //navController.navigate(Screens.LOGIN.name)
                onLoginClick
            ) {
                Text(text = "Uz mas ucet ?")
            }


            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                          onRegisterClick()
                 },
                enabled = isFieldsEmpty && !isPasswordSame
                ) {
                Text(text = "Registracia")
            }
        }

    }
}

//@Preview
//@Composable
//fun LoginPreview() {
//    RegisterScreen(navController = )
//}