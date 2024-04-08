package com.ummah.mosque.features.register.domain

import com.ummah.mosque.common.ParsingService
import com.ummah.mosque.common.SuspendParameterizedUseCase
import com.ummah.mosque.common.dto.LocationMap
import com.ummah.mosque.features.register.data.City
import com.ummah.mosque.features.register.data.CityData
import javax.inject.Inject

interface GetCitiesUseCase : SuspendParameterizedUseCase<CityData, List<City>>

class DefaultGetCitiesUseCase @Inject constructor(
    private val parsingService: ParsingService
) : GetCitiesUseCase {
    override suspend fun invoke(param: CityData): List<City> {
        val data = parsingService.decodeFromString<List<LocationMap>>(
            param.locations
        )
        val countryData = data?.find { it.countryId == param.countryId }
        return countryData?.cities?.map {
            City(name = it.name, id = it.cityId)
        }?: listOf()
    }
}