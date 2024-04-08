package com.ummah.mosque.common.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationMap(
    val cities: List<Cities>,
    val country: String,
    val countryId: Long,
    val countryCode: String
)

@Serializable
data class Cities(
    val imageUrl: String,
    val name: String,
    val cityId: Long
)