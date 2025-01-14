package com.ovais.mosqueapp.features.register.domain

import com.ovais.mosqueapp.common.SuspendParameterizedUseCase
import com.ovais.mosqueapp.common.dto.QueryResult
import com.ovais.mosqueapp.common.dto.ValidationResults
import com.ovais.mosqueapp.features.register.data.RegistrationRepository
import javax.inject.Inject

interface UsernameAvailabilityUseCase : SuspendParameterizedUseCase<String, ValidationResults>

class DefaultUsernameAvailabilityUseCase @Inject constructor(
    private val repository: RegistrationRepository
) : UsernameAvailabilityUseCase {

    override suspend fun invoke(param: String): ValidationResults {
        return if (param.isBlank()) {
            ValidationResults.Failure("Username is empty!")
        } else {
            validateUsername(username = param)
        }
    }

    private suspend fun validateUsername(username: String): ValidationResults {
        return when (val result = repository.isUsernameAvailable(username)) {
            is QueryResult.Success -> ValidationResults.Success
            is QueryResult.Failed -> ValidationResults.Failure(result.message)
        }
    }
}