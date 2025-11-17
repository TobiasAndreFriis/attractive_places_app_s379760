package com.example.attractive_places_app_s379760.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attractive_places_app_s379760.data.LagretSted
import com.example.attractive_places_app_s379760.repositories.LagretStedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(private val repository: LagretStedRepository =
                       LagretStedRepository()) : ViewModel() {
    private val _lagretsteder = MutableStateFlow<List<LagretSted>>(emptyList())
    val lagretsteder: StateFlow<List<LagretSted>> = _lagretsteder
    init {
        hentLagretSteder()
    }
    fun hentLagretSteder() {
        viewModelScope.launch {
            val list = repository.hentLagretSted()
            _lagretsteder.value = list
        }
    }
    fun leggTilLagretSted(beskrivelse: String, positivt: String, gateadresse: String, lat: Double, lng: Double) {
        viewModelScope.launch {
            try {
                repository.leggTilLagretSted(beskrivelse, positivt, gateadresse,lat,lng)
                hentLagretSteder()
            } catch (e: Exception) {
                Log.e("MapViewModel", "Feil legg inn: ${e.message}")
            }
        }
    }
}