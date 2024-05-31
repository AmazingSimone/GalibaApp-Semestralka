package com.example.galibaapp_semestralka.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.Normalizer

class CreateEventViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val mesto : MestaNaSlovensku = MestaNaSlovensku()
    private val _mesta = MutableStateFlow(mesto.getMesta())
    val mesta = searchText.combine(_mesta) { text, mesta ->
        if(text.isBlank()) {
            mesta
        } else {
            val normalizedText = normalize(text)
            mesta.filter {
                normalize(it.nazov).contains(normalizedText, ignoreCase = true)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _mesta.value
    )

    fun onSearchTextChange(text: String) {
        _isSearching.value = true
        _searchText.value = text
    }

    fun normalize(input: String): String {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}".toRegex(), "")
    }
}
