package com.example.galibaapp_semestralka.data.Home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {


    var selectedCityName = mutableStateOf("")

    var active = mutableStateOf(false)


}