package com.example.galibaapp_semestralka.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginRegisterViewModel : ViewModel() {

    private val TAG = LoginRegisterViewModel::class.simpleName
    var registrationUIState = mutableStateOf(RegistrationUIState())
    var loginUIState = mutableStateOf(LoginUIState())

    fun onRegisterEvent(event:UIevent) {
        when(event) {
            is UIevent.loginChanged -> {
                registrationUIState.value = registrationUIState.value.copy(login = event.login)
                printState()
            }

            is UIevent.passwordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(password = event.password)
                printState()
            }

            is UIevent.confirmPasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(confirmPassword = event.confirmPassword)
                printState()
            }

            is UIevent.isArtistChanged -> {
                registrationUIState.value = registrationUIState.value.copy(isArtist = event.artist)
                printState()
            }

            is UIevent.RegisterButtonClicked -> {
                signUp()
            }

            else -> {}
        }
    }

    fun onLoginEvent(event:UIevent) {
        when(event) {
            is UIevent.loginChanged -> {
                loginUIState.value = loginUIState.value.copy(login = event.login)
                Log.d(TAG, loginUIState.value.toString())

            }

            is UIevent.passwordChanged -> {
                loginUIState.value = loginUIState.value.copy(password = event.password)
                Log.d(TAG, loginUIState.value.toString())

            }

            is UIevent.LoginButtonClicked -> {
                login()
            }

            else -> {}
        }
    }


    private fun signUp() {
        Log.d(TAG, "Inside_signUp()")
        Log.d(TAG, registrationUIState.value.toString())
    }

    private fun login() {
        Log.d(TAG, "Inside_login()")
        Log.d(TAG, loginUIState.value.toString())
    }

    private fun printState() {
        Log.d(TAG, "Inside_printstate()")
        Log.d(TAG, registrationUIState.value.toString())
    }

}