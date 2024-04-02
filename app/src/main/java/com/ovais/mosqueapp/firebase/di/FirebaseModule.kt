package com.ovais.mosqueapp.firebase.di

import com.ovais.mosqueapp.firebase.DefaultFirebaseAnalyticsManager
import com.ovais.mosqueapp.firebase.DefaultFirebaseFireStoreManager
import com.ovais.mosqueapp.firebase.DefaultFirebaseRealTimeDatabaseManager
import com.ovais.mosqueapp.firebase.DefaultRemoteConfigurationManager
import com.ovais.mosqueapp.firebase.FirebaseAnalyticsManager
import com.ovais.mosqueapp.firebase.FirebaseFireStoreManager
import com.ovais.mosqueapp.firebase.FirebaseRealTimeDatabaseManager
import com.ovais.mosqueapp.firebase.RemoteConfigurationManager
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
}