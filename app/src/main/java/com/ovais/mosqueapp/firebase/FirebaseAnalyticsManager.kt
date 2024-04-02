package com.ovais.mosqueapp.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

interface FirebaseAnalyticsManager {
    fun logStringEvent(eventName: String, parameters: Map<String, String> = mapOf())
    fun logBundleEvent(eventName: String, parameters: Map<String, Bundle> = mapOf())
    fun logDoubleEvent(eventName: String, parameters: Map<String, Double> = mapOf())
    fun logLongEvent(eventName: String, parameters: Map<String, Long> = mapOf())
    fun logArrayEvent(eventName: String, parameters: Map<String, Array<Bundle>> = mapOf())
}

class DefaultFirebaseAnalyticsManager @Inject constructor() : FirebaseAnalyticsManager {
    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        Firebase.analytics
    }

    override fun logStringEvent(eventName: String, parameters: Map<String, String>) {
        firebaseAnalytics.logEvent(eventName) {
            parameters.entries.forEach { paramMap ->
                param(paramMap.key, paramMap.value)
            }
        }
    }

    override fun logArrayEvent(eventName: String, parameters: Map<String, Array<Bundle>>) {
        firebaseAnalytics.logEvent(eventName) {
            parameters.entries.forEach { paramMap ->
                param(paramMap.key, paramMap.value)
            }
        }
    }

    override fun logLongEvent(eventName: String, parameters: Map<String, Long>) {
        firebaseAnalytics.logEvent(eventName) {
            parameters.entries.forEach { paramMap ->
                param(paramMap.key, paramMap.value)
            }
        }
    }

    override fun logBundleEvent(eventName: String, parameters: Map<String, Bundle>) {
        firebaseAnalytics.logEvent(eventName) {
            parameters.entries.forEach { paramMap ->
                param(paramMap.key,paramMap.value)
            }
        }
    }

    override fun logDoubleEvent(eventName: String, parameters: Map<String, Double>) {
        firebaseAnalytics.logEvent(eventName) {
            parameters.entries.forEach { paramMap ->
                param(paramMap.key,paramMap.value)
            }
        }
    }
}