package com.example.galibaapp_semestralka.data

sealed class RegisterUIevent {

    data class usernameChanged(val username :String) : RegisterUIevent()
    data class emailChanged(val email :String) : RegisterUIevent()
    data class passwordChanged(val password :String) : RegisterUIevent()

    data class confirmPasswordChanged(val confirmPassword :String) : RegisterUIevent()

    data class isArtistChanged(val artist :Boolean) : RegisterUIevent()


    //object RegisterButtonClicked : RegisterUIevent()

}