package com.ovais.mosqueapp.features.splash.di

import com.ovais.mosqueapp.features.splash.domain.DefaultGetLocalizationUseCase
import com.ovais.mosqueapp.features.splash.domain.DefaultGetLocationMapUseCase
import com.ovais.mosqueapp.features.splash.domain.GetLocalizationUseCase
import com.ovais.mosqueapp.features.splash.domain.GetLocationMapUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface SplashModule {
    @Binds
    fun bindGetLocationMapUseCase(
        default: DefaultGetLocationMapUseCase
    ): GetLocationMapUseCase

    @Binds
    fun bindGetLocalizationUseCase(
        default: DefaultGetLocalizationUseCase
    ): GetLocalizationUseCase
}