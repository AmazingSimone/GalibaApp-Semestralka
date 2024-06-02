package com.example.galibaapp_semestralka.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime

class FirebaseViewModel : ViewModel() {

    private val TAG = FirebaseViewModel::class.simpleName

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    var myEvents = mutableStateListOf<Event?>()

    var allEvents = mutableStateListOf<Event?>()

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    val currentUserId: MutableLiveData<String> = MutableLiveData()

    val emailId: MutableLiveData<String> = MutableLiveData()

    val bio: MutableLiveData<String> = MutableLiveData()

    val username: MutableLiveData<String> = MutableLiveData()

    val isArtist: MutableLiveData<Boolean> = MutableLiveData()


    fun checkForActiveUser() {
        isUserLoggedIn.value = firebaseAuth.currentUser != null
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

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .get().addOnCompleteListener {
            if (it.isSuccessful) {
                currentUserId.value = firebaseAuth.currentUser?.uid
                val document = it.result
                if (document != null && document.exists()) {
                    username.value = document.getString("username")
                    bio.value = document.getString("bio")
                    isArtist.value = document.getBoolean("isArtist")
                    Log.d(TAG, "Username: ${username.value}")

                }
            }
        }
    }

    fun getUserName(usedId: String) : String {
        var username : String = ""
        firebaseFirestore.collection("users").document(usedId).get().addOnCompleteListener {
           username = it.result.get("username").toString()
        }
        return username
    }

    fun signUp(onSuccess: () -> Unit, onFailure: () -> Unit, registerViewModel: RegisterViewModel) {

        //var success = mutableStateOf(true)
        Log.d(TAG, "Inside signUp()")
        Log.d(TAG, registerViewModel.registrationUIState.value.toString())

        registerViewModel.registerInProgress.value = true



        firebaseAuth.createUserWithEmailAndPassword(
            registerViewModel.registrationUIState.value.email,
            registerViewModel.registrationUIState.value.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User created successfully")
                //success.value = true
                firebaseFirestore.collection("users").document(task.result.user?.uid.toString())
                    .set(
                        mapOf(
                            "username" to registerViewModel.registrationUIState.value.username,
                            "bio" to registerViewModel.registrationUIState.value.bio,
                            "isArtist" to registerViewModel.registrationUIState.value.isArtist
                        )
                    )
                onSuccess()
            } else {
                onFailure()
                //Log.d(TAG, "Error: ${task.exception?.message}")

                //success.value = false
            }
            registerViewModel.registerInProgress.value = false
        }
        //Log.d(TAG, "return success val: ${success.value}")
        //return success.value
    }

    fun login(onSuccess: () -> Unit, onFailure: () -> Unit, loginViewModel: LoginViewModel) {
        Log.d(TAG, "Inside_login()")
        Log.d(TAG, loginViewModel.loginUIState.value.toString())
        loginViewModel.loginInProgress.value = true
        firebaseAuth.signInWithEmailAndPassword(
            loginViewModel.loginUIState.value.email,
            loginViewModel.loginUIState.value.password
        ).addOnCompleteListener {
            Log.d(TAG, "${it.isSuccessful}")
            if (it.isSuccessful) {
                loginViewModel.loginInProgress.value = false
                onSuccess()
            }
        }.addOnFailureListener {
            Log.d(TAG, "login fail")
            loginViewModel.loginInProgress.value = false
            onFailure()
        }

    }

    fun logout(onSuccess: () -> Unit, onFailure: () -> Unit) {

        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Signed out ")
                onSuccess()
            } else {
                Log.d(TAG, "Signed out failed")
                onFailure()
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun updateData(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        newUsername: String?,
        newBio: String?,
        changedIsArtist: Boolean?
    ) {

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .set(
                mapOf(
                    "username" to newUsername,
                    "bio" to newBio,
                    "isArtist" to changedIsArtist
                )
            )
            .addOnSuccessListener {
                Log.d(TAG, "Updated")
                onSuccess()
            }
            .addOnFailureListener {
                Log.d(TAG, "Failure ")

                onFailure()
            }

    }



    fun createEvent(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        nazovAkcie: String,
        miestoAkcie: String,
        datumACasAkcie: LocalDateTime?,
        mesto: Mesto?,
        popisakcie: String
    ) {
        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .collection("events").add(
            mapOf(
                "eventName" to nazovAkcie,
                "location" to miestoAkcie,
                "dateAndTime" to datumACasAkcie,
                "city" to mesto,
                "eventDetails" to popisakcie,
                "userId" to firebaseAuth.currentUser?.uid
            )
        )
            .addOnSuccessListener {
                Log.d(TAG, "Event created")
                onSuccess()
            }
            .addOnFailureListener {
                Log.d(TAG, "Event failure")
                onFailure()
            }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMyMyCreatedEvents() {
        Log.d("firebaseviewmodel", "volanie metody")

        val currentEvents = mutableStateListOf<Event?>()

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .collection("events").get()
            .addOnSuccessListener {

                if (!it.isEmpty) {

                    val list = it.documents
                    for (document in list) {

                        val cityMap = document.get("city") as? HashMap<*, *>
                        val mesto = cityMap?.let {
                            val nazov = it["nazov"] as? String ?: ""
                            val skratka = it["skratka"] as? String ?: ""
                            Mesto(nazov, skratka)
                        }


                        val dateMap = document.get("dateAndTime") as? Map<*, *>
                        Log.d("firebaseviewmodel", "dateMap: $dateMap")

                        val dateAndTime = dateMap?.let {
                            val year = (it["year"] as? Long)?.toInt() ?: 0
                            val monthValue = (it["monthValue"] as? Long)?.toInt() ?: 1
                            val dayOfMonth = (it["dayOfMonth"] as? Long)?.toInt() ?: 1
                            val hour = (it["hour"] as? Long)?.toInt() ?: 0
                            val minute = (it["minute"] as? Long)?.toInt() ?: 0
                            val second = (it["second"] as? Long)?.toInt() ?: 0

                            Log.d("firebaseviewmodel", "year: $year, monthValue: $monthValue, dayOfMonth: $dayOfMonth, hour: $hour, minute: $minute, second: $second")

                            LocalDateTime.of(year, monthValue, dayOfMonth, hour, minute, second)
                        }



                        val event = Event(
                            mesto = mesto,
                            datumACas = dateAndTime,
                            popis = document.get("eventDetails").toString(),
                            nazov = document.get("eventName").toString(),
                            miesto = document.get("location").toString(),
                            userId = document.get("userId").toString()
                        )

                        currentEvents.add(event)

                    }
                    myEvents = currentEvents

                } else {


                }
            }

            .addOnFailureListener {

            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllEventsCreated() {
        val currentEvents = mutableStateListOf<Event?>()

        firebaseFirestore.collection("users").document().collection("events").get().addOnSuccessListener {
            if (!it.isEmpty) {
                Log.d("Firebaseviewmodel", "inside on success")

                val list = it.documents
                for (document in list) {

                    val cityMap = document.get("city") as? HashMap<*, *>
                    val mesto = cityMap?.let {
                        val nazov = it["nazov"] as? String ?: ""
                        val skratka = it["skratka"] as? String ?: ""
                        Mesto(nazov, skratka)
                    }


                    val dateMap = document.get("dateAndTime") as? Map<*, *>
                    Log.d("firebaseviewmodel", "dateMap: $dateMap")

                    val dateAndTime = dateMap?.let {
                        val year = (it["year"] as? Long)?.toInt() ?: 0
                        val monthValue = (it["monthValue"] as? Long)?.toInt() ?: 1
                        val dayOfMonth = (it["dayOfMonth"] as? Long)?.toInt() ?: 1
                        val hour = (it["hour"] as? Long)?.toInt() ?: 0
                        val minute = (it["minute"] as? Long)?.toInt() ?: 0
                        val second = (it["second"] as? Long)?.toInt() ?: 0

                        Log.d("firebaseviewmodel", "year: $year, monthValue: $monthValue, dayOfMonth: $dayOfMonth, hour: $hour, minute: $minute, second: $second")

                        LocalDateTime.of(year, monthValue, dayOfMonth, hour, minute, second)
                    }



                    val event = Event(
                        mesto = mesto,
                        datumACas = dateAndTime,
                        popis = document.get("eventDetails").toString(),
                        nazov = document.get("eventName").toString(),
                        miesto = document.get("location").toString(),
                        userId = document.get("userId").toString()
                    )

                    currentEvents.add(event)
                    Log.d("Firebaseviewmodel", "${currentEvents.size}")

                }
                allEvents = currentEvents
            }
        }

    }
}