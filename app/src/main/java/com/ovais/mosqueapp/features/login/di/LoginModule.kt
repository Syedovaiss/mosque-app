package com.ovais.mosqueapp.features.login.di

import com.ovais.mosqueapp.features.login.data.DefaultLoginRepository
import com.ovais.mosqueapp.features.login.data.LoginRepository
import com.ovais.mosqueapp.features.login.domain.DefaultLoginAuthenticationUseCase
import com.ovais.mosqueapp.features.login.domain.DefaultPasswordValidationUseCase
import com.ovais.mosqueapp.features.login.domain.DefaultUsernameValidationUseCase
import com.ovais.mosqueapp.features.login.domain.LoginAuthenticationUseCase
import com.ovais.mosqueapp.features.login.domain.PasswordValidationUseCase
import com.ovais.mosqueapp.features.login.domain.UsernameValidationUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface LoginModule {
    @Binds
    fun bindUsernameValidationUseCase(
        default: DefaultUsernameValidationUseCase
    ): UsernameValidationUseCase

    @Binds
    fun bindPasswordValidationUseCase(
        default: DefaultPasswordValidationUseCase
    ): PasswordValidationUseCase

    @Binds
    fun bindsLoginRepository(
        default: DefaultLoginRepository
    ): LoginRepository

    @Binds
    fun bindsLoginAuthenticationUseCase(
        default: DefaultLoginAuthenticationUseCase
    ): LoginAuthenticationUseCase
}