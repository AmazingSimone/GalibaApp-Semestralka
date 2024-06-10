package com.example.galibaapp_semestralka.data.Login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.galibaapp_semestralka.data.constrains.Validator

class LoginViewModel : ViewModel() {


    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())
    var loginInProgress = mutableStateOf(false)
    var badLogin = mutableStateOf(false)


    fun onLoginEvent(event: LoginUIevent) {
        when(event) {
            is LoginUIevent.emailChanged -> {
                loginUIState.value = loginUIState.value.copy(email = event.email)


                val usernameResult = Validator.validateEmail(loginUIState.value.email)

                loginUIState.value = loginUIState.value.copy(
                    emailErr = usernameResult.status
                )
            }

            is LoginUIevent.passwordChanged -> {
                loginUIState.value = loginUIState.value.copy(password = event.password)

                val passwordResult = Validator.validatePassword(loginUIState.value.password)

                loginUIState.value = loginUIState.value.copy(
                    passwordErr = passwordResult.status
                )
            }
        }
    }

    fun isAnyUserInputError(): Boolean {

        val emailResult = Validator.validateEmail(loginUIState.value.email)
        val passwordResult = Validator.validatePassword(loginUIState.value.password)

        loginUIState.value = loginUIState.value.copy(
            emailErr = emailResult.status,
            passwordErr = passwordResult.status,
        )
        return emailResult.status || passwordResult.status
    }
}

