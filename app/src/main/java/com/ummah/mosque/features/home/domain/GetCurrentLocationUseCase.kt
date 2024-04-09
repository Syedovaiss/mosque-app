package com.ummah.mosque.features.home.domain

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.ummah.mosque.common.SuspendUseCase
import com.ummah.mosque.common.dto.QueryResult
import com.ummah.mosque.common.utils.completeAsQueryResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface GetCurrentLocationUseCase : SuspendUseCase<LocationResults>
class DefaultGetCurrentLocationUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : GetCurrentLocationUseCase {
    private val locationProvider by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override suspend fun invoke(): LocationResults {
        return when (val result = locationProvider.lastLocation.completeAsQueryResult()) {
            is QueryResult.Success -> {
                result.data?.let { location ->
                    LocationResults.Success(
                        LatLng(
                            location.latitude,
                            location.longitude
                        )
                    )
                } ?: run {
                    LocationResults.Failure("Failed to get current location. Make sure you turned on location of your phone")
                }
            }

            is QueryResult.Failed -> {
                LocationResults.Failure(result.message)
            }
        }

    }
}

sealed interface LocationResults {
    data class Success(val latLng: LatLng) : LocationResults
    data class Failure(val error: String) : LocationResults
}