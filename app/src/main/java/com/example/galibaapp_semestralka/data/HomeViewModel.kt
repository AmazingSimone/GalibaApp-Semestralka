package com.example.galibaapp_semestralka.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {

    private val TAG = HomeViewModel::class.simpleName

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val firebaseFirestore = FirebaseFirestore.getInstance()


    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    val emailId: MutableLiveData<String> = MutableLiveData()

    val username: MutableLiveData<String> = MutableLiveData()

    val isArtist: MutableLiveData<Boolean> = MutableLiveData()


    fun checkForActiveUser() {
        isUserLoggedIn.value = FirebaseAuth.getInstance().currentUser != null
        if (isUserLoggedIn.value == true) {
            getUserData()
        }
        Log.d(TAG, "inside check for user")
        Log.d(TAG, "${isUserLoggedIn.value}")
    }

    fun getUserData() {
        firebaseAuth.currentUser?.also {
            it.email?.also { email ->
                emailId.value = email
            }
        }

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString()).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val document = it.result
                if (document != null && document.exists()) {
                    username.value = document.getString("username")
                    isArtist.value = document.getBoolean("isArtist")
                    Log.d(TAG, "Username: ${username.value}")

                }
            }
        }
    }
}