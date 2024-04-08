package com.ummah.mosque.features.login.domain

import com.ummah.mosque.common.ParamUseCase
import javax.inject.Inject

interface PasswordValidationUseCase : ParamUseCase<String, PasswordValidationResults>

class DefaultPasswordValidationUseCase @Inject constructor() : PasswordValidationUseCase {
    override fun invoke(param: String): PasswordValidationResults {
        return if (param.isNotBlank()) {
            PasswordValidationResults.Valid
        } else {
            PasswordValidationResults.EMPTY
        }
    }

}

sealed interface PasswordValidationResults {
    data object EMPTY : PasswordValidationResults
    data object Valid : PasswordValidationResults
}