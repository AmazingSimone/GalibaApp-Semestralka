package com.example.galibaapp_semestralka.data

data class RegistrationUIState (
    var login :String = "",
    var password :String = "",
    var confirmPassword :String = "",
    var isArtist :Boolean = false
)

data class LoginUIState (
    var login :String = "",
    var password :String = ""
)