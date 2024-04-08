package com.ummah.mosque.features.register.domain

import com.ummah.mosque.common.SuspendParameterizedUseCase
import com.ummah.mosque.common.dto.ValidationResults
import com.ummah.mosque.features.register.data.UserInfoUiData
import javax.inject.Inject

interface RegistrationValidationUseCase :
    SuspendParameterizedUseCase<UserInfoUiData, ValidationResults>

class DefaultRegistrationValidationUseCase @Inject constructor(
    private val usernameAvailabilityUseCase: UsernameAvailabilityUseCase
) : RegistrationValidationUseCase {
    private companion object {
        private val PAKISTAN_NUMBER_REGEX = Regex("(?:\\+92|0)3[0-9]{2}([0-9]{7})")
    }

    override suspend fun invoke(param: UserInfoUiData): ValidationResults {
        return when {
            usernameAvailabilityUseCase(param.username) is ValidationResults.Failure -> ValidationResults.Failure(
                (usernameAvailabilityUseCase(param.username) as ValidationResults.Failure).message
            )

            param.phoneNumber.isEmpty() -> ValidationResults.Failure("Phone number can't be empty")
            param.phoneNumber.matches(PAKISTAN_NUMBER_REGEX)
                .not() -> ValidationResults.Failure("Please enter a valid number which starts with +92 or 0")

            param.country.isEmpty() -> ValidationResults.Failure("Please select a valid country")
            param.city.isEmpty() -> ValidationResults.Failure("Please select a valid city")
            else -> return ValidationResults.Success
        }
    }
}