package com.ummah.mosque.features.register.domain

import com.ummah.mosque.common.ParsingService
import com.ummah.mosque.common.SuspendParameterizedUseCase
import com.ummah.mosque.common.SuspendUseCase
import com.ummah.mosque.common.dto.LocationMap
import com.ummah.mosque.common.storage.LocalStorageManager
import com.ummah.mosque.common.utils.default
import com.ummah.mosque.features.register.data.Country
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

interface GetCountriesUseCase : SuspendParameterizedUseCase<String,List<Country>>

class DefaultGetCountriesUseCase @Inject constructor(
    private val parsingService: ParsingService
) : GetCountriesUseCase {

    override suspend fun invoke(param: String): List<Country> {
        val data = parsingService.decodeFromString<List<LocationMap>>(param)
       return data?.map { map ->
           Country(
               name = map.country,
               id = map.countryId,
               code = map.countryCode
           )
        } ?: run {
            listOf()
        }
    }
}