package com.example.galibaapp_semestralka.data

sealed class LoginUIevent {


    data class emailChanged(val email :String) : LoginUIevent()

    data class passwordChanged(val password :String) : LoginUIevent()

    //object LoginButtonClicked : LoginUIevent()

}