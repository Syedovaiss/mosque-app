package com.ovais.mosqueapp.features.register.domain

import com.ovais.mosqueapp.common.ParsingService
import com.ovais.mosqueapp.common.SuspendParameterizedUseCase
import com.ovais.mosqueapp.common.dto.LocationMap
import com.ovais.mosqueapp.features.register.data.City
import com.ovais.mosqueapp.features.register.data.CityData
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