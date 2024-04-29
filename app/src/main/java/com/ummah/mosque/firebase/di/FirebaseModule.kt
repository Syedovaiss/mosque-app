package com.ummah.mosque.firebase.di

import com.ummah.mosque.firebase.DefaultFirebaseAnalyticsManager
import com.ummah.mosque.firebase.DefaultFirebaseFireStoreManager
import com.ummah.mosque.firebase.DefaultFirebaseLoggingService
import com.ummah.mosque.firebase.DefaultFirebaseRealTimeDatabaseManager
import com.ummah.mosque.firebase.DefaultFirebaseStorageManager
import com.ummah.mosque.firebase.DefaultRemoteConfigurationManager
import com.ummah.mosque.firebase.FirebaseAnalyticsManager
import com.ummah.mosque.firebase.FirebaseFireStoreManager
import com.ummah.mosque.firebase.FirebaseLoggingService
import com.ummah.mosque.firebase.FirebaseRealTimeDatabaseManager
import com.ummah.mosque.firebase.FirebaseStorageManager
import com.ummah.mosque.firebase.RemoteConfigurationManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FirebaseModule {

    @Singleton
    @Binds
    fun bindRemoteConfigurationManager(
        default: DefaultRemoteConfigurationManager
    ): RemoteConfigurationManager

    @Binds
    @Singleton
    fun bindsFirebaseAnalyticsManager(
        default: DefaultFirebaseAnalyticsManager
    ): FirebaseAnalyticsManager

    @Binds
    @Singleton
    fun bindsFirebaseRealtimeDatabaseManager(
        default: DefaultFirebaseRealTimeDatabaseManager
    ): FirebaseRealTimeDatabaseManager

    @Binds
    @Singleton
    fun bindsFirebaseFireStoreManager(
        default: DefaultFirebaseFireStoreManager
    ): FirebaseFireStoreManager

    @Binds
    @Singleton
    fun bindStorageManager(
        default: DefaultFirebaseStorageManager
    ): FirebaseStorageManager

    @Binds
    @Singleton
    fun bindFirebaseLoggingService(
        default: DefaultFirebaseLoggingService
    ): FirebaseLoggingService
}