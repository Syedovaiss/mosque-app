package com.ummah.mosque.features.login.di

import com.ummah.mosque.features.login.data.DefaultLoginRepository
import com.ummah.mosque.features.login.data.LoginRepository
import com.ummah.mosque.features.login.domain.DefaultLoginAuthenticationUseCase
import com.ummah.mosque.features.login.domain.DefaultPasswordValidationUseCase
import com.ummah.mosque.features.login.domain.DefaultUsernameValidationUseCase
import com.ummah.mosque.features.login.domain.LoginAuthenticationUseCase
import com.ummah.mosque.features.login.domain.PasswordValidationUseCase
import com.ummah.mosque.features.login.domain.UsernameValidationUseCase
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