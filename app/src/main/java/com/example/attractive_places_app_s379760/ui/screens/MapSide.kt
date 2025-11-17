package com.example.attractive_places_app_s379760.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.attractive_places_app_s379760.data.LagretSted
import com.example.attractive_places_app_s379760.viewmodels.MapViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.collections.forEach

@Composable
fun MapSide(viewModel: MapViewModel = viewModel(), innerPadding: PaddingValues) {
    val lagretSted by viewModel.lagretsteder.collectAsState()
    var newMarkerPosition by remember { mutableStateOf<LatLng?>(null) }
    var valgtSted by remember { mutableStateOf<LagretSted?>(null) }

    var beskrivelseInput by remember { mutableStateOf("") }
    var positivtInput by remember { mutableStateOf("") }
    var gateadresseInput by remember { mutableStateOf("") }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(59.912766, 10.746189), 8f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            newMarkerPosition = latLng
        }
    ) {
        lagretSted.forEach { sted ->
            Marker(
                state = MarkerState(position = LatLng(sted.lat, sted.lng)),
                title = sted.beskrivelse,
                snippet = "Klikk for detaljer",
                onClick = {
                    false
                },
                onInfoWindowClick = {
                    valgtSted = sted
                }
            )
        }
    }

    valgtSted?.let { sted ->
        AlertDialog(
            onDismissRequest = { valgtSted = null },
            title = { Text(sted.beskrivelse) },
            text = {
                Text("Positivt: ${sted.positivt}\nAdresse: ${sted.gateadresse}")
            },
            confirmButton = {
                Button(onClick = { valgtSted = null }) { Text("Lukk") }
            }
        )
    }

    // Input-dialog for å legge til nytt sted
    newMarkerPosition?.let { pos ->
        AlertDialog(
            onDismissRequest = {
                // Lukk dialog uten å lagre
                beskrivelseInput = ""
                positivtInput = ""
                gateadresseInput = ""
                newMarkerPosition = null
            },
            title = { Text("Lagre sted") },
            text = {
                Column {
                    TextField(
                        value = beskrivelseInput,
                        onValueChange = { beskrivelseInput = it },
                        label = { Text("Beskrivelse") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = positivtInput,
                        onValueChange = { positivtInput = it },
                        label = { Text("Positivt") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = gateadresseInput,
                        onValueChange = { gateadresseInput = it },
                        label = { Text("Gateadresse") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.leggTilLagretSted(
                        beskrivelseInput,
                        positivtInput,
                        gateadresseInput,
                        pos.latitude,
                        pos.longitude
                    )
                    // nullstill input og skjul dialog
                    beskrivelseInput = ""
                    positivtInput = ""
                    gateadresseInput = ""
                    newMarkerPosition = null
                }) {
                    Text("Lagre sted")
                }
            },
            dismissButton = {
                Button(onClick = {
                    // nullstill input og skjul dialog uten å lagre
                    beskrivelseInput = ""
                    positivtInput = ""
                    gateadresseInput = ""
                    newMarkerPosition = null
                }) {
                    Text("Avbryt")
                }
            }
        )
    }
}
