package com.ummah.mosque.features.createmosque.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CreateMosqueView() {
    /*

    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        val mapSettings by viewModel.mapSettings.collectAsStateWithLifecycle()
        val currentLocation = viewModel.usersLocation.collectAsStateWithLifecycle()
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
    * */
}