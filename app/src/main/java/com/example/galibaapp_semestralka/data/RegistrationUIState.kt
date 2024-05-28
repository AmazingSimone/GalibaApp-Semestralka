package com.example.galibaapp_semestralka.data

data class RegistrationUIState (
    var username :String = "",
    var email :String = "",
    var password :String = "",
    var confirmPassword :String = "",
    var isArtist :Boolean = false,

    var usernameErr : Boolean = false,
    var usernameIsEmpty : Boolean = false,
    var emailErr : Boolean = false,
    var passwordErr : Boolean = false,
    var passwordMatchErr : Boolean = false,
)

