package com.example.galibaapp_semestralka.data

import java.time.LocalDateTime

data class Event(
    var city: Mesto? = null,
    var coming: Long? = 0,
    var dateAndTime: LocalDateTime? = null,
    var eventDetails: String? = "",
    var eventName: String? = "",
    var eventPic: String? = "",
    var interested: Long? = 0,
    var location: String? = "",
    var userId: String? = "",
    var eventId: String? = ""
)