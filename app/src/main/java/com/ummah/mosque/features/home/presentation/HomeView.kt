package com.ummah.mosque.features.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ummah.mosque.R


@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        val mapSettings by viewModel.mapSettings.collectAsStateWithLifecycle()
        val currentLocation = viewModel.currentLocation.collectAsStateWithLifecycle()
        mapSettings?.let { mosqueSetting ->
            currentLocation.value?.let { info ->
                val singapore = LatLng(info.lat, info.lng)
                val cameraPositionState = rememberCameraPositionState {
                    position =
                        CameraPosition.fromLatLngZoom(singapore, mosqueSetting.zoomLevel)
                }
                val uiSettings by remember { mutableStateOf(mosqueSetting.settings) }
                val properties by remember {
                    mutableStateOf(
                        MapProperties(
                            mapType = mosqueSetting.mapType,
                            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                                context,
                                R.raw.map_dark_mode
                            )
                        )
                    )
                }
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                    uiSettings = uiSettings
                ) {
                    Marker(
                        state = MarkerState(position = singapore),
                        title = if (mosqueSetting.titleEnabled) "Singapore" else null,
                        snippet = if (mosqueSetting.titleEnabled) "Marker In Singapore" else null
                    )
                }
            }

        }
    }

}

@Preview
@Composable
private fun HomePreview() {
    HomeView()
}