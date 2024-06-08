package com.example.galibaapp_semestralka.data

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galibaapp_semestralka.data.Login.LoginViewModel
import com.example.galibaapp_semestralka.data.Register.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime

class FirebaseViewModel : ViewModel() {

    private val TAG = FirebaseViewModel::class.simpleName

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val firebaseStorage = FirebaseStorage.getInstance()

    var myEvents = mutableStateListOf<Event?>()

    //var userEvents = mutableStateListOf<Event?>()

    private val _allEvents = MutableStateFlow<List<Event?>>(emptyList())
    val allEvents: StateFlow<List<Event?>> = _allEvents

    private val _allEventsByCity = MutableStateFlow<List<Event?>>(emptyList())
    val allEventsByCity: StateFlow<List<Event?>> = _allEventsByCity

    private val _allEventsByUser = MutableStateFlow<List<Event?>>(emptyList())
    val allEventsByUser: StateFlow<List<Event?>> = _allEventsByUser

    private val _myInterestedEvents = MutableStateFlow<List<Event?>>(emptyList())
    val myInterestedEvents: StateFlow<List<Event?>> = _myInterestedEvents

    private val _myComingEvents = MutableStateFlow<List<Event?>>(emptyList())
    val myComingEvents: StateFlow<List<Event?>> = _myComingEvents

    //var eventsByCity = mutableStateListOf<Event?>()

    var chosenEvent: MutableLiveData<Event?> = MutableLiveData()

    var chosenUser: MutableLiveData<User?> = MutableLiveData()

    val isFollowing: MutableLiveData<Boolean> = MutableLiveData()

    var myFollowedUsers = mutableStateListOf<String?>()

    //var chosenCity = mutableStateListOf<Mesto?>()

    //var myFavouriteCities = mutableStateListOf<Mesto?>()

    private val _myFavouriteCities = MutableStateFlow<List<Mesto>>(emptyList())
    val myFavouriteCities: StateFlow<List<Mesto>> = _myFavouriteCities

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    val currentUserId: MutableLiveData<String> = MutableLiveData()

    val emailId: MutableLiveData<String> = MutableLiveData()

    val bio: MutableLiveData<String> = MutableLiveData()

    val username: MutableLiveData<String> = MutableLiveData()

    val isArtist: MutableLiveData<Boolean> = MutableLiveData()

    val profilePic: MutableLiveData<String> = MutableLiveData()


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
                        profilePic.value = document.get("profilePic").toString()
                        isArtist.value = document.getBoolean("isArtist")
                        Log.d(TAG, "Username: ${username.value}")
                    }
                }
            }


    }

//    fun getUserName(
//        onSuccess: (String) -> Unit,
//        onFailure: () -> Unit,
//        usedId: String
//    ): String {
//        var username: String = ""
//        firebaseFirestore.collection("users").document(usedId).get().addOnSuccessListener {
//            username = it.get("username").toString()
//            onSuccess(username)
//        }.addOnFailureListener {
//            onFailure()
//        }
//        return username
//    }

    fun getUserData(
        onSuccess: (User) -> Unit,
        onFailure: () -> Unit,
        usedId: String
    ): User {
        val user: User = User()
        firebaseFirestore.collection("users").document(usedId).get().addOnSuccessListener {
            user.username = it.get("username").toString()
            user.isArtist = it.get("isArtist") as Boolean?
            user.profilePic = it.get("profilePic").toString()
            user.bio = it.get("bio").toString()
            onSuccess(user)
        }.addOnFailureListener {
            onFailure()
        }
        return user
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
            .update(
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
        eventPic: Uri? = null,
        miestoAkcie: String,
        datumACasAkcie: LocalDateTime?,
        mesto: Mesto?,
        popisakcie: String
    ) {

        if (eventPic != null) {
            firebaseStorage.reference.child("userCreatedEventsPictures/${firebaseAuth.currentUser?.uid.toString()}.jpg").putFile(eventPic).addOnSuccessListener {
                firebaseStorage.reference.child("userCreatedEventsPictures/${firebaseAuth.currentUser?.uid.toString()}.jpg").downloadUrl.addOnSuccessListener {
                    firebaseFirestore.collection("events").add(
                        mapOf(
                            "eventName" to nazovAkcie,
                            "eventPic" to it.toString(),
                            "location" to miestoAkcie,
                            "dateAndTime" to datumACasAkcie,
                            "city" to mesto,
                            "eventDetails" to popisakcie,
                            "userId" to firebaseAuth.currentUser?.uid,
                            "interested" to 0,
                            "coming" to 0

                        )
                    )
                        .addOnSuccessListener {
                            onSuccess()
                            Log.d("neuveritelnyVyberacFotiek", "success ${it.toString()}")
                        }
                        .addOnFailureListener {
                            onFailure()
                            Log.d("neuveritelnyVyberacFotiek", "ulozil ale nedal do db")
                        }
                }.addOnFailureListener {
                    onFailure()
                    Log.d("neuveritelnyVyberacFotiek", "ulozil ale nanasiel db")

                }
            }
                .addOnFailureListener {
                    onFailure()
                    Log.d("neuveritelnyVyberacFotiek", "fail")
                }
        } else {
            firebaseFirestore.collection("events").add(
                mapOf(
                    "eventName" to nazovAkcie,
                    "eventPic" to eventPic,
                    "location" to miestoAkcie,
                    "dateAndTime" to datumACasAkcie,
                    "city" to mesto,
                    "eventDetails" to popisakcie,
                    "userId" to firebaseAuth.currentUser?.uid,
                    "interested" to 0,
                    "coming" to 0

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




    }

    fun interestState(
        isNotInterested: () -> Unit,
        isInterested: () -> Unit,
        isComing: () -> Unit,
        eventId: String
    ) {
        //val currentUserUid = firebaseAuth.currentUser?.uid
        if (firebaseAuth.currentUser?.uid != null) {
            firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
                .collection("interestedEvents")
                .document(eventId).get().addOnSuccessListener { interestedEventDoc ->
                    if (interestedEventDoc.exists()) {
                        isInterested()
                    } else {
                        firebaseFirestore.collection("users")
                            .document(firebaseAuth.currentUser?.uid.toString())
                            .collection("comingEvents")
                            .document(eventId).get().addOnSuccessListener { comingEventDoc ->
                                if (comingEventDoc.exists()) {
                                    isComing()
                                } else {
                                    isNotInterested()
                                }
                            }.addOnFailureListener {
                                isNotInterested()
                            }
                    }
                }.addOnFailureListener {
                    isNotInterested()
                }
        } else {
            isNotInterested()
        }
    }


    fun addInterested(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        eventId: String
    ) {
        // Aktualizuj stĺpec "interested" v dokumente eventu
        val eventRef = firebaseFirestore.collection("events").document(eventId)
        firebaseFirestore.runTransaction { transaction ->
            val event = transaction.get(eventRef)
            val currentInterested = event.getLong("interested") ?: 0
            transaction.update(eventRef, "interested", currentInterested + 1)
        }.addOnSuccessListener {
            // Ak sa aktualizácia úspešne vykonala, pridaj referenciu na event do kolekcie "interestedEvents" používateľa
            Log.d(TAG, "interested")

            firebaseFirestore.collection("users").document(firebaseAuth.uid.toString())
                .collection("interestedEvents").document(eventId).set(
                    mapOf(
                        "eventRef" to firebaseFirestore.collection("events").document(eventId)
                    )
                )
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
        }.addOnFailureListener {
            onFailure()
        }
    }

    fun removeInterested(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        eventId: String
    ) {
        val eventRef = firebaseFirestore.collection("events").document(eventId)

        // Spustenie transakcie pre aktualizáciu stĺpca "interested" v dokumente eventu
        firebaseFirestore.runTransaction { transaction ->
            val event = transaction.get(eventRef)
            val currentInterested = event.getLong("interested") ?: 0
            // Odpocitaj 1 od počtu záujemcov
            transaction.update(eventRef, "interested", currentInterested - 1)
        }.addOnSuccessListener {
            // Ak sa aktualizácia úspešne vykonala, odstráň referenciu na event z kolekcie "interestedEvents" používateľa

            firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
                .collection("interestedEvents").document(eventId).delete()
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }

        }.addOnFailureListener {
            onFailure()
        }
    }


    fun addComming(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        eventId: String
    ) {
        val eventRef = firebaseFirestore.collection("events").document(eventId)
        firebaseFirestore.runTransaction { transaction ->
            val event = transaction.get(eventRef)
            val currentInterested = event.getLong("coming") ?: 0
            transaction.update(eventRef, "coming", currentInterested + 1)
        }.addOnSuccessListener {
            // Ak sa aktualizácia úspešne vykonala, pridaj referenciu na event do kolekcie "interestedEvents" používateľa


            firebaseFirestore.collection("users").document(firebaseAuth.uid.toString())
                .collection("comingEvents").document(eventId).set(
                    mapOf(
                        "eventRef" to firebaseFirestore.collection("events").document(eventId)
                    )
                )
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
        }.addOnFailureListener {
            onFailure()
        }
    }

    fun removeComming(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        eventId: String
    ) {
        val eventRef = firebaseFirestore.collection("events").document(eventId)

        firebaseFirestore.runTransaction { transaction ->
            val event = transaction.get(eventRef)
            val currentInterested = event.getLong("coming") ?: 0
            transaction.update(eventRef, "coming", currentInterested - 1)
        }.addOnSuccessListener {

            firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
                .collection("comingEvents").document(eventId).delete()
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }

        }.addOnFailureListener {
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
                                eventPic = document.get("eventPic").toString(),
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
                                coming = document.get("coming") as Long?,
                                dateAndTime = dateAndTime,
                                eventDetails = document.get("eventDetails").toString(),
                                eventName = document.get("eventName").toString(),
                                eventPic = document.get("eventPic").toString(),
                                interested = document.get("interested") as Long?,
                                location = document.get("location").toString(),
                                userId = document.get("userId").toString(),
                                eventId = document.id.toString()
                            )

                            currentUserEvents.add(event)
                        }
                        //userEvents = currentUserEvents
                        _allEventsByUser.value = currentUserEvents
                        //_allEvents.value = currentUserEvents
                    } else {
                        Log.d("firebaseviewmodel", "No events found for the current user.")
                        //userEvents.clear()
                        _allEventsByUser.value = emptyList()
                        //_allEvents.value = emptyList()
                    }
                }
                .addOnFailureListener {
                    //userEvents.clear()
                    _allEventsByUser.value = emptyList()
                    //_allEvents.value = emptyList()

                }

        } else if (byUserId.isEmpty() && byCity != null) {


            val currentCityEvents = mutableStateListOf<Event?>()

            val cityToHash = hashMapOf(
                "nazov" to byCity.nazov,
                "skratka" to byCity.skratka
            )

            firebaseFirestore.collection("events")
                .whereEqualTo("city", cityToHash)
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

                            Log.d(
                                "createdEventList",
                                "inside byCity success, mesto ${mesto.toString()}"
                            )


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
                                coming = document.get("coming") as Long?,
                                dateAndTime = dateAndTime,
                                eventDetails = document.get("eventDetails").toString(),
                                eventName = document.get("eventName").toString(),
                                eventPic = document.get("eventPic").toString(),
                                interested = document.get("interested") as Long?,
                                location = document.get("location").toString(),
                                userId = document.get("userId").toString(),
                                eventId = document.id.toString()

                            )

                            currentCityEvents.add(event)
                            Log.d("createdEventList", "inside byCity end ${event.toString()}")
                        }
                        //eventsByCity = currentCityEvents
                        //_allEvents.value = currentCityEvents
                        _allEventsByCity.value = currentCityEvents

                    } else {
                        Log.d("createdEventList", "inside byCity failure")

                        //eventsByCity.clear()
                        _allEventsByCity.value = emptyList()
                        //_allEvents.value = emptyList()
                    }
                }
                .addOnFailureListener {
                    _allEventsByCity.value = emptyList()

                }

        } else {
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
                            coming = document.get("coming") as Long?,
                            dateAndTime = dateAndTime,
                            eventDetails = document.get("eventDetails").toString(),
                            eventName = document.get("eventName").toString(),
                            eventPic = document.get("eventPic").toString(),
                            interested = document.get("interested") as Long?,
                            location = document.get("location").toString(),
                            userId = document.get("userId").toString(),
                            eventId = document.id.toString()

                        )

                        currentEvents.add(event)

                    }
                    _allEvents.value = currentEvents
                } else {
                    _allEvents.value = emptyList()
                }
            }
                .addOnFailureListener {
                    _allEvents.value = emptyList()
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
                coming = it.get("coming") as Long?,
                dateAndTime = dateAndTime,
                eventDetails = it.get("eventDetails").toString(),
                eventName = it.get("eventName").toString(),
                eventPic = it.get("eventPic").toString(),
                interested = it.get("interested") as Long?,
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMyInterestedEvents() {
        val currentEvents = mutableStateListOf<Event?>()

        firebaseFirestore.collection("users").document(firebaseAuth.uid.toString())
            .collection("interestedEvents")
            .get().addOnSuccessListener {
                if (!it.isEmpty) {
                    val list = it.documents

                    for (document in list) {
                        document.getDocumentReference("eventRef")?.get()
                            ?.addOnSuccessListener {
                                if (it.exists()) {
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
                                        LocalDateTime.of(year,monthValue, dayOfMonth, hour, minute, second)
                                    }

                                    val event = Event(
                                        city = mesto,
                                        coming = it.get("coming") as Long?,
                                        dateAndTime = dateAndTime,
                                        eventDetails = it.get("eventDetails").toString(),
                                        eventName = it.get("eventName").toString(),
                                        interested = it.get("interested") as Long?,
                                        location = it.get("location").toString(),
                                        userId = it.get("userId").toString(),
                                        eventId = it.id.toString()
                                    )

                                    currentEvents.add(event)
                                }
                                _myInterestedEvents.value = currentEvents
                            }
                    }
                } else {
                    _myInterestedEvents.value = emptyList()
                }
            }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMyComingEvents() {
        val currentEvents = mutableStateListOf<Event?>()

        firebaseFirestore.collection("users").document(firebaseAuth.uid.toString())
            .collection("comingEvents")
            .get().addOnSuccessListener { it ->
                if (!it.isEmpty) {
                    val list = it.documents

                    for (document in list) {
                        document.getDocumentReference("eventRef")?.get()
                            ?.addOnSuccessListener {
                                if (it.exists()) {
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
                                        LocalDateTime.of(year,monthValue, dayOfMonth, hour, minute, second)
                                    }

                                    val event = Event(
                                        city = mesto,
                                        coming = it.get("coming") as Long?,
                                        dateAndTime = dateAndTime,
                                        eventDetails = it.get("eventDetails").toString(),
                                        eventName = it.get("eventName").toString(),
                                        interested = it.get("interested") as Long?,
                                        location = it.get("location").toString(),
                                        userId = it.get("userId").toString(),
                                        eventId = it.id.toString()
                                    )

                                    currentEvents.add(event)
                                }
                                _myComingEvents.value = currentEvents
                            }
                    }
                } else {
                    _myComingEvents.value = emptyList()
                }
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
        eventPic: Uri? = null,
        dateAndTime: LocalDateTime?,
        eventDetails: String,
        eventName: String?,
        location: String?
    ) {
        //Log.d("firebaseviewmodel", "event id : ${eventId.toString()}")
        Log.d("mestoAkcie", " v update fun ${city.toString()}")

        if (eventPic != null) {
            firebaseStorage.reference.child("userCreatedEventsPictures/${firebaseAuth.currentUser?.uid.toString()}.jpg").putFile(eventPic).addOnSuccessListener {
                firebaseStorage.reference.child("userCreatedEventsPictures/${firebaseAuth.currentUser?.uid.toString()}.jpg").downloadUrl.addOnSuccessListener {
                    firebaseFirestore.collection("events").document(eventId.toString()).update(
                        mapOf(
                            "city" to city,
                            "dateAndTime" to dateAndTime,
                            "eventDetails" to eventDetails,
                            "eventName" to eventName,
                            "eventPic" to it.toString(),
                            "location" to location,
                        )
                    )
                        .addOnSuccessListener {
                            onSuccess()
                            Log.d("neuveritelnyVyberacFotiek", "success ${it.toString()}")
                        }
                        .addOnFailureListener {
                            onFailure()
                            Log.d("neuveritelnyVyberacFotiek", "ulozil ale nedal do db")
                        }
                }.addOnFailureListener {
                    onFailure()
                    Log.d("neuveritelnyVyberacFotiek", "ulozil ale nanasiel db")

                }
            }
                .addOnFailureListener {
                    onFailure()
                    Log.d("neuveritelnyVyberacFotiek", "fail")
                }
        } else {
            firebaseFirestore.collection("events").document(eventId.toString())
                .update(

                    mapOf(
                        "city" to city,
                        "dateAndTime" to dateAndTime,
                        "eventDetails" to eventDetails,
                        "eventName" to eventName,
                        "eventPic" to eventPic,
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
                profilePic = it.get("profilePic").toString(),
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

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .collection("followedUsers").document(followedUserId)
            .set(
                mapOf(
                    "userRef" to firebaseFirestore.collection("users").document(followedUserId)
                )
            )
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure()
            }
    }

    fun unFollow(
        onUnfollowSuccess: () -> Unit,
        onUnfollowFailure: () -> Unit,
        followedUserId: String
    ) {
        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .collection("followedUsers")
            .document(followedUserId.toString()).delete().addOnSuccessListener {
                onUnfollowSuccess()
            }.addOnFailureListener {
                onUnfollowFailure()

            }
    }

    fun isFollowing(
        followedUserId: String
    ) {

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .collection("followedUsers").document(followedUserId).get()
            .addOnSuccessListener {
                isFollowing.value = it.exists()
            }
            .addOnFailureListener {
                isFollowing.value = false
            }
    }

    fun getMyFollowingList() {
        val currentFollowedUsers = mutableStateListOf<String?>()

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .collection("followedUsers").get().addOnSuccessListener {
                if (!it.isEmpty) {
                    val list = it.documents
                    for (document in list) {

                        val followerId = document.id.toString()

                        currentFollowedUsers.add(followerId)
                    }

                    myFollowedUsers = currentFollowedUsers
                } else {
                    myFollowedUsers.clear()
                }
            }
    }

    fun addToMyFavouriteCities(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        onExists: () -> Unit,
        city: Mesto
    ) {

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .collection("favouriteCities")
            .whereEqualTo("city.nazov", city.nazov).get().addOnSuccessListener {
                if (it.isEmpty) {
                    firebaseFirestore.collection("users")
                        .document(firebaseAuth.currentUser?.uid.toString())
                        .collection("favouriteCities").document()
                        .set(mapOf("city" to city))
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener {
                            onFailure()
                        }
                } else {
                    onExists() // Or you can show a message indicating the city is already in the list
                }
            }.addOnFailureListener {
                onFailure()
            }

    }


    fun getMyFavouriteCities() {
        val currentFavouriteCities = mutableStateListOf<Mesto>()

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .collection("favouriteCities").get().addOnSuccessListener {
                if (!it.isEmpty) {
                    val list = it.documents
                    for (document in list) {

                        val cityMap = document.get("city") as HashMap<*, *>
                        val mesto = cityMap.let {
                            val nazov = it["nazov"] as String
                            val skratka = it["skratka"] as String
                            Mesto(nazov, skratka)
                        }
                        currentFavouriteCities.add(mesto)
                    }

                    _myFavouriteCities.value = currentFavouriteCities
                    Log.d("favCities", "${myFavouriteCities.value.size}")
                } else {
                    _myFavouriteCities.value = emptyList()
                }
            }
    }

    fun removeFromMyFavouriteCities(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        cityToRemove: Mesto
    ) {
        val cityToHash = hashMapOf(
            "nazov" to cityToRemove.nazov,
            "skratka" to cityToRemove.skratka
        )

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
            .collection("favouriteCities").whereEqualTo("city", cityToHash).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (document in it.documents) {
                        firebaseFirestore.collection("users")
                            .document(firebaseAuth.currentUser?.uid.toString())
                            .collection("favouriteCities").document(document.id).delete()
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener {
                                onFailure()
                            }
                    }
                } else {
                    onFailure()
                }
            }
            .addOnFailureListener {
                onFailure()
            }
    }


    fun saveToUserProfilePictures(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        uri: Uri
    ) {


        firebaseStorage.reference.child("userProfileImages/${firebaseAuth.uid.toString()}.jpg").putFile(uri).addOnSuccessListener {
            firebaseStorage.reference.child("userProfileImages/${firebaseAuth.uid.toString()}.jpg").downloadUrl.addOnSuccessListener {
                firebaseFirestore.collection("users").document(firebaseAuth.uid.toString()).update("profilePic", it.toString())
                        .addOnSuccessListener {
                            onSuccess()
                            Log.d("neuveritelnyVyberacFotiek", "success")
                        }
                        .addOnFailureListener {
                            onFailure()
                            Log.d("neuveritelnyVyberacFotiek", "ulozil ale nedal do db")
                        }
                }.addOnFailureListener {
                    onFailure()
                Log.d("neuveritelnyVyberacFotiek", "ulozil ale nanasiel db")

            }
            }
            .addOnFailureListener {
                onFailure()
                Log.d("neuveritelnyVyberacFotiek", "fail")
            }
    }

    fun saveToUserCreatedEventPictures(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        eventId: String,
        uri: Uri
    ) {


        firebaseStorage.reference.child("userCreatedEventsPictures/${eventId.toString()}.jpg").putFile(uri).addOnSuccessListener {
            firebaseStorage.reference.child("userCreatedEventsPictures/${eventId.toString()}.jpg").downloadUrl.addOnSuccessListener {
                firebaseFirestore.collection("events").document(eventId.toString()).update("eventPic", it.toString())
                    .addOnSuccessListener {
                        onSuccess()
                        Log.d("neuveritelnyVyberacFotiek", "success")
                    }
                    .addOnFailureListener {
                        onFailure()
                        Log.d("neuveritelnyVyberacFotiek", "ulozil ale nedal do db")
                    }
            }.addOnFailureListener {
                onFailure()
                Log.d("neuveritelnyVyberacFotiek", "ulozil ale nanasiel db")

            }
        }
            .addOnFailureListener {
                onFailure()
                Log.d("neuveritelnyVyberacFotiek", "fail")
            }
    }


}