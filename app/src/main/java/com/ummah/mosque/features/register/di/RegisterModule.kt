package com.ummah.mosque.features.register.di

import com.ummah.mosque.features.register.data.DefaultRegistrationRepository
import com.ummah.mosque.features.register.data.RegistrationRepository
import com.ummah.mosque.features.register.domain.AddUsernameUseCase
import com.ummah.mosque.features.register.domain.DefaultAddUsernameUseCase
import com.ummah.mosque.features.register.domain.DefaultGetCitiesUseCase
import com.ummah.mosque.features.register.domain.DefaultGetCountriesUseCase
import com.ummah.mosque.features.register.domain.DefaultRegisterUserUseCase
import com.ummah.mosque.features.register.domain.DefaultRegistrationValidationUseCase
import com.ummah.mosque.features.register.domain.DefaultUsernameAvailabilityUseCase
import com.ummah.mosque.features.register.domain.GetCitiesUseCase
import com.ummah.mosque.features.register.domain.GetCountriesUseCase
import com.ummah.mosque.features.register.domain.RegisterUserUseCase
import com.ummah.mosque.features.register.domain.RegistrationValidationUseCase
import com.ummah.mosque.features.register.domain.UsernameAvailabilityUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface RegisterModule {
    @Binds
    fun bindGetCountriesUseCase(
        default: DefaultGetCountriesUseCase
    ): GetCountriesUseCase

    @Binds
    fun bindGetCitiesUseCase(
        default: DefaultGetCitiesUseCase
    ): GetCitiesUseCase

    @Binds
    fun bindUsernameAvailabilityUseCase(
        default: DefaultUsernameAvailabilityUseCase
    ): UsernameAvailabilityUseCase

    @Binds
    fun bindRegisterRepository(
        default: DefaultRegistrationRepository
    ): RegistrationRepository

    @Binds
    fun bindValidationUseCase(
        default: DefaultRegistrationValidationUseCase
    ): RegistrationValidationUseCase

    @Binds
    fun bindAddUsernameUseCase(
        default: DefaultAddUsernameUseCase
    ): AddUsernameUseCase

    @Binds
    fun bindsRegisterUseUseCase(
        default: DefaultRegisterUserUseCase
    ): RegisterUserUseCase
}