package com.ovais.mosqueapp.features.register.di

import com.ovais.mosqueapp.features.register.data.DefaultRegistrationRepository
import com.ovais.mosqueapp.features.register.data.RegistrationRepository
import com.ovais.mosqueapp.features.register.domain.AddUsernameUseCase
import com.ovais.mosqueapp.features.register.domain.DefaultAddUsernameUseCase
import com.ovais.mosqueapp.features.register.domain.DefaultGetCitiesUseCase
import com.ovais.mosqueapp.features.register.domain.DefaultGetCountriesUseCase
import com.ovais.mosqueapp.features.register.domain.DefaultRegisterUserUseCase
import com.ovais.mosqueapp.features.register.domain.DefaultRegistrationValidationUseCase
import com.ovais.mosqueapp.features.register.domain.DefaultUsernameAvailabilityUseCase
import com.ovais.mosqueapp.features.register.domain.GetCitiesUseCase
import com.ovais.mosqueapp.features.register.domain.GetCountriesUseCase
import com.ovais.mosqueapp.features.register.domain.RegisterUserUseCase
import com.ovais.mosqueapp.features.register.domain.RegistrationValidationUseCase
import com.ovais.mosqueapp.features.register.domain.UsernameAvailabilityUseCase
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