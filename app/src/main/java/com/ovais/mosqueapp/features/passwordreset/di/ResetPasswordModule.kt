package com.ovais.mosqueapp.features.passwordreset.di

import com.ovais.mosqueapp.features.passwordreset.domain.DefaultPasswordUpdateRepository
import com.ovais.mosqueapp.features.passwordreset.domain.DefaultPasswordUpdateUseCase
import com.ovais.mosqueapp.features.passwordreset.domain.PasswordUpdateRepository
import com.ovais.mosqueapp.features.passwordreset.domain.PasswordUpdateUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface ResetPasswordModule {
    @Binds
    fun bindResetPasswordUseCase(
        default: DefaultPasswordUpdateUseCase
    ): PasswordUpdateUseCase

    @Binds
    fun bindPasswordResetRepo(
        default: DefaultPasswordUpdateRepository
    ): PasswordUpdateRepository
}