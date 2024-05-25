package com.example.galibaapp_semestralka.data

sealed class UIevent {

    data class loginChanged(val login :String) : UIevent()

    data class passwordChanged(val password :String) : UIevent()

    data class confirmPasswordChanged(val confirmPassword :String) : UIevent()

    data class isArtistChanged(val artist :Boolean) : UIevent()


    object RegisterButtonClicked : UIevent()

    object LoginButtonClicked : UIevent()

}