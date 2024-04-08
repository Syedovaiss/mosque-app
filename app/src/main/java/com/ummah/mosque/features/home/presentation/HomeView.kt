package com.ummah.mosque.features.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun HomeView() {
    Column(modifier = Modifier.fillMaxSize()) {
        val singapore = LatLng(1.35, 103.87)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(singapore, 10f)
        }
        val uiSettings by remember { mutableStateOf(MapUiSettings()) }
        val properties by remember {
            mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
           uiSettings = uiSettings
        ) {
            Marker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }

}

@Preview
@Composable
private fun HomePreview() {
    HomeView()
}