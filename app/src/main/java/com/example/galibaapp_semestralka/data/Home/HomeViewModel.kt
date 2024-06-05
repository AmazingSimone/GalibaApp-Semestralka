package com.example.galibaapp_semestralka.data.Home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.galibaapp_semestralka.data.Mesto

class HomeViewModel : ViewModel() {

    var selectedCityName = mutableStateOf("Rovno za nosom")

    var active = mutableStateOf(false)


    //val oblubeneMesta: List<Mesto?> = listOf(null)

    var oblubeneMesta = mutableStateListOf<Mesto?>()

}