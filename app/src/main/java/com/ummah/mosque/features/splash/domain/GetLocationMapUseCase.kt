package com.ummah.mosque.features.splash.domain

import com.ummah.mosque.common.UseCase
import com.ummah.mosque.firebase.RemoteConfigurationManager
import javax.inject.Inject

interface GetLocationMapUseCase : UseCase<String>

class DefaultGetLocationMapUseCase @Inject constructor(
    private val remoteConfigurationManager: RemoteConfigurationManager,
) : GetLocationMapUseCase {
    private companion object {
        private const val KEY_LOCATION_MAP = "location_map"
    }

    override fun invoke(): String {
        return remoteConfigurationManager.getString(KEY_LOCATION_MAP)
    }
}