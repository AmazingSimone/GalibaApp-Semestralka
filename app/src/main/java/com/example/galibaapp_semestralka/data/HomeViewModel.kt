package com.example.galibaapp_semestralka.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {

    val isUserLoggedIn : MutableLiveData<Boolean> = MutableLiveData()

    private val TAG = HomeViewModel::class.simpleName

    val emailId : MutableLiveData<String> = MutableLiveData()

    fun checkForActiveUser() {
        isUserLoggedIn.value = FirebaseAuth.getInstance().currentUser != null
        Log.d(TAG, "inside check for user")
        Log.d(TAG, "${isUserLoggedIn.value}")
    }

    fun getUserData() {
        FirebaseAuth.getInstance().currentUser?.also {
            it.email?.also {email->
                emailId.value = email
            }
        }
    }

}