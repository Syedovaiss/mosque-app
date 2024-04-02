package com.ovais.mosqueapp.features.register.domain

import com.ovais.mosqueapp.common.ParsingService
import com.ovais.mosqueapp.common.SuspendParameterizedUseCase
import com.ovais.mosqueapp.common.SuspendUseCase
import com.ovais.mosqueapp.common.dto.LocationMap
import com.ovais.mosqueapp.common.storage.LocalStorageManager
import com.ovais.mosqueapp.common.utils.default
import com.ovais.mosqueapp.features.register.data.Country
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