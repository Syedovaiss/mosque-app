package com.ummah.mosque.features.home.domain

import com.google.maps.android.compose.MapUiSettings
import com.ummah.mosque.common.ParsingService
import com.ummah.mosque.common.UseCase
import com.ummah.mosque.common.dto.MosqueMapSettings
import com.ummah.mosque.common.dto.MosqueMapType
import com.ummah.mosque.common.utils.default
import com.ummah.mosque.firebase.RemoteConfigurationManager
import javax.inject.Inject

interface GetMapSettingsUseCase : UseCase<MapSettings>

class DefaultGetMapSettingsUseCase @Inject constructor(
    private val remoteConfigurationManager: RemoteConfigurationManager,
    private val parsingService: ParsingService
) : GetMapSettingsUseCase {
    private companion object {
        private const val MAP_SETTINGS = "map_settings"
    }

    private val mapSettings by lazy {
        parsingService.decodeFromString<MosqueMapSettings>(
            remoteConfigurationManager.getString(MAP_SETTINGS)
        )
    }

    override fun invoke(): MapSettings {
        return mapSettings?.let { settings ->
            MapSettings.Success(
                settings = MosqueMapSettingsData(
                    mapType = MosqueMapType.getMapType(settings.mapType.default()),
                    settings = MapUiSettings(
                        compassEnabled = settings.compassEnabled.default(),
                        indoorLevelPickerEnabled = settings.indoorLevelPickerEnabled.default(),
                        mapToolbarEnabled = settings.mapToolbarEnabled.default(),
                        myLocationButtonEnabled = settings.myLocationButtonEnabled.default(),
                        rotationGesturesEnabled = settings.rotationGesturesEnabled.default(),
                        scrollGesturesEnabled = settings.scrollGesturesEnabled.default(),
                        scrollGesturesEnabledDuringRotateOrZoom = settings.scrollGesturesEnabledDuringRotateOrZoom.default(),
                        tiltGesturesEnabled = settings.tiltGesturesEnabled.default(),
                        zoomControlsEnabled = settings.zoomControlsEnabled.default(),
                        zoomGesturesEnabled = settings.zoomGesturesEnabled.default()
                    ),
                    zoomLevel = settings.zoomLevel?.toFloat()
                        .default(MosqueMapSettings.default.zoomLevel?.toFloat()),
                    titleEnabled = settings.titleEnabled.default()
                )
            )

        } ?: run {
            MapSettings.Failure("Failed to fetch settings from server!")
        }
    }
}


sealed interface MapSettings {
    data class Success(
        val settings: MosqueMapSettingsData
    ) : MapSettings

    data class Failure(val error: String) : MapSettings
}

