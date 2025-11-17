package com.example.attractive_places_app_s379760

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.attractive_places_app_s379760.ui.MapViewModel
import com.example.attractive_places_app_s379760.ui.theme.Attractive_places_app_s379760Theme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Attractive_places_app_s379760Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MapView(mapViewModel = MapViewModel(), innerPadding)
                }
            }
        }
    }
}

@Composable
fun MapView(mapViewModel: MapViewModel, innerPadding:
PaddingValues
) {
    var markers by remember { mutableStateOf(emptyList<LatLng>())
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                59.912766,
                10.746189),
            5f
        )
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        cameraPositionState = cameraPositionState,
        onMapClick = { markers = markers + it }
    )
    { markers.forEach {
        Marker(
            state= MarkerState(position = it),
            title = "Min markør",
            snippet = "Min tekst"
        )
    }
    }
}