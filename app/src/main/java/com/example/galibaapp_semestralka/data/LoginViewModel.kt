package com.example.galibaapp_semestralka.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())


    fun onLoginEvent(event:LoginUIevent) {
        when(event) {
            is LoginUIevent.emailChanged -> {
                loginUIState.value = loginUIState.value.copy(email = event.email)
                Log.d(TAG, loginUIState.value.toString())
            }

            is LoginUIevent.passwordChanged -> {
                loginUIState.value = loginUIState.value.copy(password = event.password)
                Log.d(TAG, loginUIState.value.toString())

            }

            is LoginUIevent.LoginButtonClicked -> {
                login()
            }
        }
    }

    private fun login() {
        Log.d(TAG, "Inside_login()")
        Log.d(TAG, loginUIState.value.toString())

    }

}

