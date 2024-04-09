package com.ummah.mosque.common.dto

import com.google.maps.android.compose.MapType
import kotlinx.serialization.Serializable

@Serializable
data class MosqueMapSettings(
    val compassEnabled: Boolean?,
    val indoorLevelPickerEnabled: Boolean?,
    val mapToolbarEnabled: Boolean?,
    val mapType: String?,
    val myLocationButtonEnabled: Boolean?,
    val rotationGesturesEnabled: Boolean?,
    val scrollGesturesEnabled: Boolean?,
    val scrollGesturesEnabledDuringRotateOrZoom: Boolean?,
    val tiltGesturesEnabled: Boolean?,
    val titleEnabled: Boolean?,
    val zoomControlsEnabled: Boolean?,
    val zoomGesturesEnabled: Boolean?,
    val zoomLevel: Int?
) {
    companion object {
        val default = MosqueMapSettings(
            compassEnabled = false,
            indoorLevelPickerEnabled = false,
            mapToolbarEnabled = false,
            mapType = MosqueMapType.NORMAL.identifier,
            myLocationButtonEnabled = false,
            rotationGesturesEnabled = false,
            scrollGesturesEnabled = true,
            scrollGesturesEnabledDuringRotateOrZoom = true,
            tiltGesturesEnabled = true,
            titleEnabled = false,
            zoomLevel = 20,
            zoomControlsEnabled = true,
            zoomGesturesEnabled = true
        )
    }
}

@Serializable
enum class MosqueMapType(val identifier: String) {
    NORMAL("NORMAL"),
    SATELLITE("SATELLITE"),
    TERRAIN("TERRAIN"),
    NONE("NONE"),
    HYBRID("HYBRID");

    companion object {
        fun getMapType(identifier: String): MapType = when (identifier) {
            NORMAL.identifier -> MapType.NORMAL

            SATELLITE.identifier -> MapType.SATELLITE

            TERRAIN.identifier -> MapType.TERRAIN

            NONE.identifier -> MapType.NONE

            HYBRID.identifier -> MapType.HYBRID

            else -> MapType.NORMAL
        }
    }
}