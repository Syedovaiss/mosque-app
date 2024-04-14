package com.ummah.mosque.features.home.di

import com.ummah.mosque.features.home.data.DefaultHomeRepository
import com.ummah.mosque.features.home.data.HomeRepository
import com.ummah.mosque.features.home.domain.DefaultGetCarouselImagesUseCase
import com.ummah.mosque.features.home.domain.DefaultGetCurrentLocationUseCase
import com.ummah.mosque.features.home.domain.DefaultGetMapSettingsUseCase
import com.ummah.mosque.features.home.domain.GetCarouselImagesUseCase
import com.ummah.mosque.features.home.domain.GetCurrentLocationUseCase
import com.ummah.mosque.features.home.domain.GetMapSettingsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface HomeModule {
    @Binds
    fun bindGetLocationUseCase(
        default: DefaultGetCurrentLocationUseCase
    ): GetCurrentLocationUseCase

    @Binds
    fun bindMapSettingUseCase(
        default: DefaultGetMapSettingsUseCase
    ): GetMapSettingsUseCase

    @Binds
    fun bindHomeRepository(
        default: DefaultHomeRepository
    ): HomeRepository

    @Binds
    fun bindGetCarouselImagesUseCase(
        default: DefaultGetCarouselImagesUseCase
    ): GetCarouselImagesUseCase
}