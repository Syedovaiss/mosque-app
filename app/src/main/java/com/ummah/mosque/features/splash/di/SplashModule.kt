package com.ummah.mosque.features.splash.di

import com.ummah.mosque.features.splash.domain.DefaultGetLocalizationUseCase
import com.ummah.mosque.features.splash.domain.DefaultGetLocationMapUseCase
import com.ummah.mosque.features.splash.domain.GetLocalizationUseCase
import com.ummah.mosque.features.splash.domain.GetLocationMapUseCase
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