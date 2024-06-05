package com.example.galibaapp_semestralka.data.Login

data class LoginUIState (
    var email :String = "",
    var password :String = "",

    var emailErr : Boolean = false,
    var passwordErr : Boolean = false
)