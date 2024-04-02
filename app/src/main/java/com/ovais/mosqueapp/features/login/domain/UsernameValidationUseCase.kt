package com.ovais.mosqueapp.features.login.domain

import com.ovais.mosqueapp.common.ParamUseCase
import javax.inject.Inject

interface UsernameValidationUseCase : ParamUseCase<String, UsernameValidationResults>

class DefaultUsernameValidationUseCase @Inject constructor() : UsernameValidationUseCase {

    override fun invoke(param: String): UsernameValidationResults {
        return if (param.isNotBlank()) {
            UsernameValidationResults.Valid
        } else {
            UsernameValidationResults.EMPTY
        }
    }
}

sealed interface UsernameValidationResults {
    data object EMPTY : UsernameValidationResults
    data object Valid : UsernameValidationResults
}