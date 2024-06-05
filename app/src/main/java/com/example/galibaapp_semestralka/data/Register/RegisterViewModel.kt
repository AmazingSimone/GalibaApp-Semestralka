package com.example.galibaapp_semestralka.data.Register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.galibaapp_semestralka.data.constrains.Validator

//data class Register (
//    var login :String = "",
//    var password :String = "",
//    var isArtist :Boolean = false
//)

class RegisterViewModel : ViewModel() {
    private val TAG = RegisterViewModel::class.simpleName
    var registrationUIState = mutableStateOf(RegistrationUIState())
    var registerInProgress = mutableStateOf(false)
//    val firebaseAuth = FirebaseAuth.getInstance()
//    val firebaseFirestore = FirebaseFirestore.getInstance()


    fun onRegisterEvent(event: RegisterUIevent) {
        when (event) {
            is RegisterUIevent.usernameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(username = event.username)

                val usernameResult = Validator.validateUsername(registrationUIState.value.username)
                val usernameIsEmptyResult = Validator.usernameIsEmpty(registrationUIState.value.username)

                registrationUIState.value = registrationUIState.value.copy(
                    usernameErr = usernameResult.status,
                    usernameIsEmpty = usernameIsEmptyResult.status
                )
            }

            is RegisterUIevent.emailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(email = event.email)

                val emailResult = Validator.validateEmail(registrationUIState.value.email)

                registrationUIState.value = registrationUIState.value.copy(
                    emailErr = emailResult.status
                )
            }

            is RegisterUIevent.passwordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(password = event.password)

                val passwordResult = Validator.validatePassword(registrationUIState.value.password)
                registrationUIState.value = registrationUIState.value.copy(
                    passwordErr = passwordResult.status
                )
            }

            is RegisterUIevent.confirmPasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(confirmPassword = event.confirmPassword)

                val password2Result = Validator.passwordMatch(registrationUIState.value.password, registrationUIState.value.confirmPassword)
                registrationUIState.value = registrationUIState.value.copy(
                    passwordMatchErr = password2Result.status
                )
            }

            is RegisterUIevent.isArtistChanged -> {
                registrationUIState.value = registrationUIState.value.copy(isArtist = event.isArtist)
            }

//            is RegisterUIevent.RegisterButtonClicked -> {
//                signUp()
//            }
        }
    }

//    fun signUp(onSuccess: () -> Unit, onFailure: () -> Unit) {
//
//        //var success = mutableStateOf(true)
//        Log.d(TAG, "Inside signUp()")
//        Log.d(TAG, registrationUIState.value.toString())
//
//        registerInProgress.value = true
//
//
//
//        firebaseAuth.createUserWithEmailAndPassword(
//            registrationUIState.value.email,
//            registrationUIState.value.password
//        ).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Log.d(TAG, "User created successfully")
//                //success.value = true
//                firebaseFirestore.collection("users").document(task.result.user?.uid.toString()).set(mapOf(
//                    "username" to registrationUIState.value.username,
//                    "bio" to registrationUIState.value.bio,
//                    "isArtist" to registrationUIState.value.isArtist
//                ))
//                onSuccess()
//            } else {
//                onFailure()
//                //Log.d(TAG, "Error: ${task.exception?.message}")
//
//                //success.value = false
//            }
//
//
//            registerInProgress.value = false
//        }
//        //Log.d(TAG, "return success val: ${success.value}")
//        //return success.value
//    }


    fun isAnyUserInputError(): Boolean {
        val usernameResult = Validator.validateUsername(registrationUIState.value.username)
        val usernameIsEmptyResult = Validator.usernameIsEmpty(registrationUIState.value.username)
        val emailResult = Validator.validateEmail(registrationUIState.value.email)
        val passwordResult = Validator.validatePassword(registrationUIState.value.password)
        val password2Result = Validator.passwordMatch(registrationUIState.value.password, registrationUIState.value.confirmPassword)
//        Log.d(TAG, "Validation")
//        Log.d(TAG, "$usernameResult")
//        Log.d(TAG, "$emailResult")
//        Log.d(TAG, "$passwordResult")
//        Log.d(TAG, "$password2Result")

        registrationUIState.value = registrationUIState.value.copy(
            usernameErr = usernameResult.status,
            usernameIsEmpty = usernameIsEmptyResult.status,
            emailErr = emailResult.status,
            passwordErr = passwordResult.status,
            passwordMatchErr = password2Result.status
        )
        return usernameResult.status || usernameIsEmptyResult.status || emailResult.status || passwordResult.status || password2Result.status
    }
}