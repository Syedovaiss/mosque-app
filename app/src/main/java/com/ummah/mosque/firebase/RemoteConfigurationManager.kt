package com.ummah.mosque.firebase

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.ummah.mosque.R
import com.ummah.mosque.events.AppEvents
import javax.inject.Inject

interface RemoteConfigurationManager {
    suspend fun activate()
    fun getString(key: String): String
}

class DefaultRemoteConfigurationManager @Inject constructor(
    private val firebaseAnalyticsManager: FirebaseAnalyticsManager
) : RemoteConfigurationManager {
    private val remoteConfig: FirebaseRemoteConfig by lazy {
        Firebase.remoteConfig
    }
    private val configSettings by lazy {
        remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
    }

    init {
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    override suspend fun activate() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val updated = task.result
                firebaseAnalyticsManager.logStringEvent(eventName = AppEvents.RemoteConfigActivated.toString())
                Log.e("remote-config-activated", updated.toString())

            } else {
                firebaseAnalyticsManager.logStringEvent(eventName = AppEvents.RemoteConfigActivationFailed.toString())
                Log.e("remote-config-failure", task.exception?.stackTraceToString().toString())
            }
        }
    }

    override fun getString(key: String): String {
        return remoteConfig.getString(key)
    }
}