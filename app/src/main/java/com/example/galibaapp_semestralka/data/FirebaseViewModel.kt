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

    var userEvents = mutableStateListOf<Event?>()

    var allEvents = mutableStateListOf<Event?>()

    var eventsByCity = mutableStateListOf<Event?>()

    var chosenEvent: MutableLiveData<Event?> = MutableLiveData()

    var chosenUser: MutableLiveData<User?> = MutableLiveData()

    var users = mutableStateListOf<User?>()

    //var chosenCity = mutableStateListOf<Mesto?>()

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    val currentUserId: MutableLiveData<String> = MutableLiveData()

    val emailId: MutableLiveData<String> = MutableLiveData()

    val bio: MutableLiveData<String> = MutableLiveData()

    val username: MutableLiveData<String> = MutableLiveData()

    val isArtist: MutableLiveData<Boolean> = MutableLiveData()


    fun checkForActiveUser() {
        isUserLoggedIn.value = firebaseAuth.currentUser != null
        if (isUserLoggedIn.value == true) {
            getCurrentUserData()
        }
        Log.d(TAG, "inside check for user")
        Log.d(TAG, "${isUserLoggedIn.value}")
    }

    fun getCurrentUserData() {
        firebaseAuth.currentUser?.also {
            it.email?.also { email ->
                emailId.value = email
            }
        }

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    currentUserId.value = firebaseAuth.currentUser?.uid.toString()
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

    fun getUserName(
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit,
        usedId: String
    ): String {
        var username: String = ""
        firebaseFirestore.collection("users").document(usedId).get().addOnSuccessListener {
            username = it.get("username").toString()
            onSuccess(username)
        }.addOnFailureListener {
            onFailure()
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
        ).addOnSuccessListener {
            //if (task.isSuccessful) {
            Log.d(TAG, "User created successfully")
            //success.value = true
            firebaseFirestore.collection("users").document(firebaseAuth.uid.toString())
                .set(
                    mapOf(
                        "username" to registerViewModel.registrationUIState.value.username,
                        "bio" to registerViewModel.registrationUIState.value.bio,
                        "isArtist" to registerViewModel.registrationUIState.value.isArtist
                    )
                )
            onSuccess()
        }.addOnFailureListener {
            onFailure()

        }
        //Log.d(TAG, "Error: ${task.exception?.message}")

        //success.value = false

        registerViewModel.registerInProgress.value = false
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

    fun deleteUser(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        userId: String
    ) {
        firebaseFirestore.collection("events")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val batch = firebaseFirestore.batch()
                for (document in querySnapshot.documents) {
                    batch.delete(document.reference)
                }
                // Commit the batch
                batch.commit()
            }


        firebaseFirestore.collection("users").document(userId.toString())
            .delete()
            .addOnSuccessListener {
                // Step 2: Delete the user from Firebase Authentication
                firebaseAuth.currentUser?.delete()
                    ?.addOnSuccessListener {
                        onSuccess()
                    }
                    ?.addOnFailureListener {
                        onFailure()
                    }
            }
            .addOnFailureListener { exception ->
            }
    }

    fun updateUserData(
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
        firebaseFirestore.collection("events").add(
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
    fun getMyCreatedEvents() {
        Log.d("firebaseviewmodel", "volanie metody")

        val currentEvents = mutableStateListOf<Event?>()
        val currentUser = firebaseAuth.currentUser

        currentUser?.let { user ->
            firebaseFirestore.collection("events")
                .whereEqualTo("userId", user.uid)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val list = querySnapshot.documents
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

                                Log.d(
                                    "firebaseviewmodel",
                                    "year: $year, monthValue: $monthValue, dayOfMonth: $dayOfMonth, hour: $hour, minute: $minute, second: $second"
                                )

                                LocalDateTime.of(year, monthValue, dayOfMonth, hour, minute, second)
                            }

                            val event = Event(
                                city = mesto,
                                dateAndTime = dateAndTime,
                                eventDetails = document.get("eventDetails").toString(),
                                eventName = document.get("eventName").toString(),
                                location = document.get("location").toString(),
                                userId = document.get("userId").toString(),
                                eventId = document.id
                            )

                            currentEvents.add(event)
                        }
                        myEvents = currentEvents
                    } else {
                        Log.d("firebaseviewmodel", "No events found for the current user.")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("firebaseviewmodel", "Error fetching events", e)
                }
        } ?: run {
            Log.e("firebaseviewmodel", "User is not logged in.")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllEventsCreated(byUserId: String = "", byCity: Mesto? = null) {
        if (byUserId.isNotEmpty() && byCity == null) {
            val currentUserEvents = mutableStateListOf<Event?>()

            //currentUser?.let { user ->
            Log.d("firebaseviewmodel", "$byUserId")

            firebaseFirestore.collection("events")
                .whereEqualTo("userId", byUserId)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val list = querySnapshot.documents
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

                                Log.d(
                                    "firebaseviewmodel",
                                    "year: $year, monthValue: $monthValue, dayOfMonth: $dayOfMonth, hour: $hour, minute: $minute, second: $second"
                                )

                                LocalDateTime.of(year, monthValue, dayOfMonth, hour, minute, second)
                            }

                            val event = Event(
                                city = mesto,
                                dateAndTime = dateAndTime,
                                eventDetails = document.get("eventDetails").toString(),
                                eventName = document.get("eventName").toString(),
                                location = document.get("location").toString(),
                                userId = document.get("userId").toString(),
                                eventId = document.id.toString()

                            )

                            currentUserEvents.add(event)
                        }
                        userEvents = currentUserEvents
                    } else {
                        Log.d("firebaseviewmodel", "No events found for the current user.")
                        userEvents.clear()

                    }
                }
                .addOnFailureListener {
                    userEvents.clear()

                }
//            } ?: run {
//                Log.e("firebaseviewmodel", "User is not logged in.")
            //}
        }
        else if (byUserId.isEmpty() && byCity != null) {

            val currentCityEvents = mutableStateListOf<Event?>()
            val city = HashMap<String, String>()
            city[byCity.nazov] = byCity.skratka

            //currentUser?.let { user ->
            firebaseFirestore.collection("events")
                .whereEqualTo("city", city)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val list = querySnapshot.documents
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

                                Log.d(
                                    "firebaseviewmodel",
                                    "year: $year, monthValue: $monthValue, dayOfMonth: $dayOfMonth, hour: $hour, minute: $minute, second: $second"
                                )

                                LocalDateTime.of(year, monthValue, dayOfMonth, hour, minute, second)
                            }

                            val event = Event(
                                city = mesto,
                                dateAndTime = dateAndTime,
                                eventDetails = document.get("eventDetails").toString(),
                                eventName = document.get("eventName").toString(),
                                location = document.get("location").toString(),
                                userId = document.get("userId").toString(),
                                eventId = document.id.toString()
                            )

                            currentCityEvents.add(event)
                        }
                        eventsByCity = currentCityEvents
                    } else {
                        eventsByCity.clear()

                    }
                }

        }
        else {
            val currentEvents = mutableStateListOf<Event?>()

            firebaseFirestore.collection("events").get().addOnSuccessListener {
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

                            Log.d(
                                "firebaseviewmodel",
                                "year: $year, monthValue: $monthValue, dayOfMonth: $dayOfMonth, hour: $hour, minute: $minute, second: $second"
                            )

                            LocalDateTime.of(year, monthValue, dayOfMonth, hour, minute, second)
                        }

                        val event = Event(
                            city = mesto,
                            dateAndTime = dateAndTime,
                            eventDetails = document.get("eventDetails").toString(),
                            eventName = document.get("eventName").toString(),
                            location = document.get("location").toString(),
                            userId = document.get("userId").toString(),
                            eventId = document.id.toString()

                        )

                        currentEvents.add(event)

                    }
                    allEvents = currentEvents
                } else {
                    allEvents.clear()
                    Log.d("userEvents","inside else clear ${userEvents.size}")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getEventData(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        eventId: String
    ) {
        firebaseFirestore.collection("events").document(eventId).get().addOnSuccessListener {


            val cityMap = it.get("city") as? HashMap<*, *>
            val mesto = cityMap?.let {
                val nazov = it["nazov"] as? String ?: ""
                val skratka = it["skratka"] as? String ?: ""
                Mesto(nazov, skratka)
            }


            val dateMap = it.get("dateAndTime") as? Map<*, *>

            val dateAndTime = dateMap?.let {
                val year = (it["year"] as? Long)?.toInt() ?: 0
                val monthValue = (it["monthValue"] as? Long)?.toInt() ?: 1
                val dayOfMonth = (it["dayOfMonth"] as? Long)?.toInt() ?: 1
                val hour = (it["hour"] as? Long)?.toInt() ?: 0
                val minute = (it["minute"] as? Long)?.toInt() ?: 0
                val second = (it["second"] as? Long)?.toInt() ?: 0

                LocalDateTime.of(year, monthValue, dayOfMonth, hour, minute, second)
            }

            val event = Event(
                city = mesto,
                dateAndTime = dateAndTime,
                eventDetails = it.get("eventDetails").toString(),
                eventName = it.get("eventName").toString(),
                location = it.get("location").toString(),
                userId = it.get("userId").toString(),
                eventId = it.id.toString()
            )


            //Log.d("Firebaseviewmodel", "${currentEvents.size}")


            chosenEvent.value = event
            Log.d("firebaseviewmodel", "chosenEvent: ${event.toString()}")

            onSuccess()

        }.addOnFailureListener {
            onFailure()
        }
    }

    fun deleteEvent(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        eventId: String
    ) {
        firebaseFirestore.collection("events").document(eventId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure()
            }
    }

    fun updateEventInfo(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        eventId: String?,
        city: Mesto?,
        dateAndTime: LocalDateTime?,
        eventDetails: String,
        eventName: String?,
        location: String?
    ) {
        //Log.d("firebaseviewmodel", "event id : ${eventId.toString()}")
        Log.d("mestoAkcie" ," v update fun ${city.toString()}")

        firebaseFirestore.collection("events").document(eventId.toString())
            .update(

                mapOf(
                    "city" to city,
                    "dateAndTime" to dateAndTime,
                    "eventDetails" to eventDetails,
                    "eventName" to eventName,
                    "location" to location,
                )
            ).addOnSuccessListener {
                Log.d("firebaseviewmodel", "inside update")
                onSuccess()
            }.addOnFailureListener {
                Log.d("firebaseviewmodel", "inside update failure")

                onFailure()
            }
    }

    fun selectUser(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        userId: String,
    ) {
        firebaseFirestore.collection("users").document(userId).get().addOnSuccessListener {

            chosenUser.value = User(
                bio = it.get("bio").toString(),
                isArtist = it.get("isArtist") as Boolean?,
                username = it.get("username").toString(),
                userId = userId.toString()
            )
            onSuccess()

        }.addOnFailureListener {
            onFailure()
        }
    }

    fun giveFollow(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        followedUserId: String
    ) {

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString()).collection("following").add(
            mapOf("followedUserId" to followedUserId)
        ).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure()
        }

    }

    fun unFollow(
        onUnfollowSuccess: () -> Unit,
        onUnfollowFailure: () -> Unit,
        followedUserId: String
    ) {
        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString()).collection("following")
            .document(followedUserId.toString()).delete().addOnFailureListener {
                onUnfollowSuccess()
            }.addOnFailureListener {
                Log.d("unfollow" ,"fail : $it")
                onUnfollowFailure()

            }
    }

}