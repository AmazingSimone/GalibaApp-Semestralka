package com.example.galibaapp_semestralka.data

import java.time.LocalDateTime

data class Event(
    var city: Mesto? = null,
    var dateAndTime: LocalDateTime? = null,
    var eventDetails: String? = "",
    var eventName: String? = "",
    var location: String? = "",
    var userId: String? = "",
    var eventId: String? = ""
)