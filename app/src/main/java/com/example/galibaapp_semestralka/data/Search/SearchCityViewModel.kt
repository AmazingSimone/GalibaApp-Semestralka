package com.example.galibaapp_semestralka.data.Search

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galibaapp_semestralka.data.FirebaseViewModel
import com.example.galibaapp_semestralka.data.MestaNaSlovensku
import com.example.galibaapp_semestralka.data.Mesto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.Normalizer

class SearchCityViewModel : ViewModel() {

    //var defaultText by mutableStateOf("")

    //var defaultSearch : String = ""
    val _searchText = MutableStateFlow("")

    val searchText = _searchText.asStateFlow()

    val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    val searchIsNotMatchingWithResult = MutableStateFlow(true)


    private val _selectedMesto = MutableStateFlow<Mesto?>(null)
    val selectedMesto: StateFlow<Mesto?> get() = _selectedMesto

    val dynamicSize = MutableStateFlow(65.dp)

    private val mesto: MestaNaSlovensku = MestaNaSlovensku()
    private val _mesta = MutableStateFlow(mesto.getMesta())
    val mestaForEditCreateEvent = searchText.combine(_mesta) { text, mesta ->

        if (text.isNotBlank() && _isSearching.value) {
            val normalizedText = normalize(text)
            mesta.filter {
                normalize(it.nazov).contains(normalizedText, ignoreCase = true)
            }.take(3)
        } else {
            dynamicSize.value = 65.dp
            emptyList()
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _mesta.value
        )

    val mestaForSearch = searchText.combine(_mesta) { text, mesta ->

            val normalizedText = normalize(text)
            mesta.filter {
                normalize(it.nazov).contains(normalizedText, ignoreCase = true)
            }
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

    fun setDefaultText(firebaseViewModel: FirebaseViewModel) {
        _searchText.value = firebaseViewModel.chosenEvent.value?.city?.nazov.toString()
        //Log.d("firebaseviewmodel" ,"df2: ${defaultSearch}")

    }

    private fun normalize(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}".toRegex(), "")
    }

}