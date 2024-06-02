package com.example.galibaapp_semestralka.data

import java.time.LocalDateTime

data class Event(
    var mesto: Mesto? = null,
    var datumACas: LocalDateTime? = null,
    var popis: String? = "",
    var nazov: String? = "",
    var miesto: String? = "",
    var userId: String? = ""
)