package com.example.galibaapp_semestralka.data.constrains

object Validator {

    fun validateUsername(username : String) : Result {
        return Result(username.length > 15)
    }

    fun usernameIsEmpty(username : String) : Result {
        return Result(username.isEmpty())
    }

    fun validateEmail(email : String) : Result {
        return Result('@' !in email)
    }

    fun validatePassword(password : String) : Result {
        return Result(password.length < 6)
    }

    fun passwordMatch(password: String, password2 : String) : Result {
        return Result(password != password2)
    }

}

data class Result(
    val status : Boolean = false
)