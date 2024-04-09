package com.ummah.mosque.features.home.domain

import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

data class MosqueMapSettingsData(
    val mapType: MapType,
    val settings: MapUiSettings,
    val zoomLevel: Float,
    val titleEnabled: Boolean
)
