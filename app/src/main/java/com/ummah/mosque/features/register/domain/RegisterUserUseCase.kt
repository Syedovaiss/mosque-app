package com.ummah.mosque.features.register.domain

import com.ummah.mosque.common.SuspendParameterizedUseCase
import com.ummah.mosque.common.dto.QueryResult
import com.ummah.mosque.features.register.data.RegistrationRepository
import com.ummah.mosque.features.register.data.UserInfo
import com.ummah.mosque.features.register.data.UserInfo.Companion.asHashMap
import javax.inject.Inject

interface RegisterUserUseCase : SuspendParameterizedUseCase<UserInfo, RegistrationResults>

class DefaultRegisterUserUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) : RegisterUserUseCase {
    private companion object {

    }
    override suspend fun invoke(param: UserInfo): RegistrationResults {
        return when (val result = registrationRepository.registerUser(param.asHashMap())) {
            is QueryResult.Success -> RegistrationResults.Success
            is QueryResult.Failed -> RegistrationResults.Failure(result.message)
        }
    }
}

sealed interface RegistrationResults {
    data object Success : RegistrationResults
    data class Failure(val message: String) : RegistrationResults
}