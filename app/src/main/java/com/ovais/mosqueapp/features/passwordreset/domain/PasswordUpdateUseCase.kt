package com.ovais.mosqueapp.features.passwordreset.domain

import com.ovais.mosqueapp.common.SuspendParameterizedUseCase
import com.ovais.mosqueapp.features.passwordreset.data.PasswordResetData
import javax.inject.Inject

interface PasswordUpdateUseCase : SuspendParameterizedUseCase<PasswordResetData, PasswordResetResults>

class DefaultPasswordUpdateUseCase @Inject constructor(
    private val repository: PasswordUpdateRepository
) : PasswordUpdateUseCase {
    override suspend fun invoke(param: PasswordResetData): PasswordResetResults {
        return repository.updatePassword(phoneNumber = param.phoneNumber, password = param.password)
    }
}


sealed interface PasswordResetResults {
    data class Success(val userId: String) : PasswordResetResults
    data class Failure(val message: String) : PasswordResetResults
}