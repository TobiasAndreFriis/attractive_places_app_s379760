package com.example.attractive_places_app_s379760.repositories

import android.util.Log
import com.example.attractive_places_app_s379760.data.LagretSted
import com.example.attractive_places_app_s379760.nettverk.Api

class LagretStedRepository() {
    suspend fun hentLagretSted(): List<LagretSted> {
        return try {
            Api.retrofitService.getLagretSted()
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun leggTilLagretSted(beskrivelse: String, positivt: String, gateadresse: String, lat: Double, lng: Double) {
        try {
            val gpsKoordinater = "${lat},${lng}"
            Api.retrofitService.putLagretSted(beskrivelse, positivt, gateadresse, gpsKoordinater)
        } catch (e: Exception) {
            Log.e("LagretStedRepository", "Feil legg inn: ${e.message}")
        }
    }
}