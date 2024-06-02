package com.example.galibaapp_semestralka.data

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.Normalizer

class CreateEventViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    val searchIsNotMatchingWithResult = MutableStateFlow(true)


    private val _selectedMesto = MutableStateFlow<Mesto?>(null)
    val selectedMesto: StateFlow<Mesto?> get() = _selectedMesto

    val dynamicSize = MutableStateFlow(0.dp)

    private val mesto : MestaNaSlovensku = MestaNaSlovensku()
    private val _mesta = MutableStateFlow(mesto.getMesta())
    val mesta = searchText.combine(_mesta) { text, mesta ->

        if (text.isNotBlank() && _isSearching.value) {
            val normalizedText = normalize(text)
            mesta.filter {
                normalize(it.nazov).contains(normalizedText, ignoreCase = true)
            }.take(3)
        } else {
            dynamicSize.value = 65.dp
            emptyList()
        }

        //}
    }
        .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _mesta.value
    )

    fun onSearchTextChange(text: String) {
        _isSearching.value = true
        //searchIsNotMatchingWithResult.value = true
        dynamicSize.value = 160.dp
        _searchText.value = text
    }


    fun chooseMesto(mesto: Mesto) {
        _selectedMesto.value = mesto
        //searchIsNotMatchingWithResult.value = _searchText.value != mesto.nazov
        _isSearching.value = false

        //TODO to sa mi nepaci jak pracuje lebo ked sa pomylim a opravim tak to nezmizne to menu
        _searchText.value = mesto.nazov
    }

//    private fun isNotMatchingWithResult(mesto: Mesto) : Boolean {
//        return _searchText.value != mesto.nazov
//    }

    private fun normalize(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}".toRegex(), "")
    }
}
