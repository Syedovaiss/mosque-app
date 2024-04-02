package com.ovais.mosqueapp.features.register.domain

import com.ovais.mosqueapp.common.SuspendParameterizedUseCase
import com.ovais.mosqueapp.common.dto.QueryResult
import com.ovais.mosqueapp.features.register.data.RegistrationRepository
import javax.inject.Inject

interface AddUsernameUseCase : SuspendParameterizedUseCase<String, AddUsernameResults>

class DefaultAddUsernameUseCase @Inject constructor(
    private val repository: RegistrationRepository
) : AddUsernameUseCase {
    override suspend fun invoke(param: String): AddUsernameResults {
        return when (val result = repository.insertUsername(username = param)) {
            is QueryResult.Success -> AddUsernameResults.Success
            is QueryResult.Failed -> AddUsernameResults.Failure(result.message)
        }
    }
}

sealed interface AddUsernameResults {
    data object Success : AddUsernameResults
    data class Failure(val message: String) : AddUsernameResults
}