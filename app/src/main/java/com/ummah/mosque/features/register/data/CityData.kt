package com.ummah.mosque.features.register.data

import kotlinx.serialization.Serializable

@Serializable
data class CityData(
    val locations: String,
    val countryId: Long
)
