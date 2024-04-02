package com.ovais.mosqueapp.common.di


import com.ovais.mosqueapp.common.DefaultDispatcherProvider
import com.ovais.mosqueapp.common.DefaultLocaleManager
import com.ovais.mosqueapp.common.DefaultToastManager
import com.ovais.mosqueapp.common.DispatcherProvider
import com.ovais.mosqueapp.common.LocaleManager
import com.ovais.mosqueapp.common.ToastManager
import com.ovais.mosqueapp.common.datetime.DateTimeManager
import com.ovais.mosqueapp.common.datetime.DefaultDateTimeManager
import com.ovais.mosqueapp.common.storage.DefaultLocalStorageManager
import com.ovais.mosqueapp.common.storage.DefaultStorageManager
import com.ovais.mosqueapp.common.storage.LocalStorageManager
import com.ovais.mosqueapp.common.storage.StorageManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface CommonModule {

    @Binds
    fun bindToastManager(
        default: DefaultToastManager
    ): ToastManager


    @Binds
    fun bindDateTimeManager(
        default: DefaultDateTimeManager
    ): DateTimeManager

    @Binds
    fun bindStorageManager(
        default: DefaultStorageManager
    ): StorageManager


    @Binds
    fun bindDispatcherProvider(
        default: DefaultDispatcherProvider
    ): DispatcherProvider

    @Binds
    fun bindsLocaleManager(
        default: DefaultLocaleManager
    ): LocaleManager
}

@Module
@InstallIn(SingletonComponent::class)
class CommonProviderModule() {

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        ignoreUnknownKeys = true
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface StorageModule {
    @Binds
    @Singleton
    fun bindLocalStorageManager(
        default: DefaultLocalStorageManager
    ): LocalStorageManager
}