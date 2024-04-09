package com.ummah.mosque.common.di


import com.ummah.mosque.common.DefaultDispatcherProvider
import com.ummah.mosque.common.DefaultLocaleManager
import com.ummah.mosque.common.DefaultPermissionManager
import com.ummah.mosque.common.DefaultToastManager
import com.ummah.mosque.common.DispatcherProvider
import com.ummah.mosque.common.LocaleManager
import com.ummah.mosque.common.PermissionManager
import com.ummah.mosque.common.ToastManager
import com.ummah.mosque.common.datetime.DateTimeManager
import com.ummah.mosque.common.datetime.DefaultDateTimeManager
import com.ummah.mosque.common.storage.DefaultLocalStorageManager
import com.ummah.mosque.common.storage.DefaultStorageManager
import com.ummah.mosque.common.storage.LocalStorageManager
import com.ummah.mosque.common.storage.StorageManager
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

    @Binds
    fun bindPermissionManager(
        default: DefaultPermissionManager
    ): PermissionManager
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