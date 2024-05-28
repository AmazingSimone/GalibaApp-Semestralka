package com.example.galibaapp_semestralka.data

data class LoginUIState (
    var email :String = "",
    var password :String = "",

    var emailErr : Boolean = false,
    var passwordErr : Boolean = false
)