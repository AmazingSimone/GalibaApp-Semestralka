package com.example.galibaapp_semestralka.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.galibaapp_semestralka.data.constrains.Validator

class LoginViewModel : ViewModel() {


    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())
    var loginInProgress = mutableStateOf(false)
    var badLogin = mutableStateOf(false)


    fun onLoginEvent(event:LoginUIevent) {
        when(event) {
            is LoginUIevent.emailChanged -> {
                loginUIState.value = loginUIState.value.copy(email = event.email)
                Log.d(TAG, loginUIState.value.toString())


                val usernameResult = Validator.validateEmail(loginUIState.value.email)

                loginUIState.value = loginUIState.value.copy(
                    emailErr = usernameResult.status
                )
            }

            is LoginUIevent.passwordChanged -> {
                loginUIState.value = loginUIState.value.copy(password = event.password)
                Log.d(TAG, loginUIState.value.toString())

                val passwordResult = Validator.validatePassword(loginUIState.value.password)

                loginUIState.value = loginUIState.value.copy(
                    passwordErr = passwordResult.status
                )

            }

//            is LoginUIevent.LoginButtonClicked -> {
//                login(onSuccess: () -> Unit, onFailure: () -> Unit)
//            }
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

//    fun login(onSuccess: () -> Unit, onFailure: () -> Unit) {
//        Log.d(TAG, "Inside_login()")
//        Log.d(TAG, loginUIState.value.toString())
//        loginInProgress.value = true
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(loginUIState.value.email,loginUIState.value.password).addOnCompleteListener {
//            Log.d(TAG, "${it.isSuccessful}")
//            if (it.isSuccessful) {
//                loginInProgress.value = false
//                onSuccess()
//            }
//        }.addOnFailureListener{
//            Log.d(TAG, "login fail")
//            loginInProgress.value = false
//            onFailure()
//        }
//
//    }
//
//    fun logout(onSuccess: () -> Unit, onFailure: () -> Unit) {
//        val firebaseAuth = FirebaseAuth.getInstance()
//
//        firebaseAuth.signOut()
//
//        val authStateListener = AuthStateListener {
//            if (it.currentUser == null) {
//                Log.d(TAG, "Signed out ")
//                onSuccess()
//            } else {
//                Log.d(TAG, "Signed out failed")
//                onFailure()
//            }
//        }
//
//        firebaseAuth.addAuthStateListener(authStateListener)
//    }

}

