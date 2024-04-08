package com.ummah.mosque.features.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummah.mosque.app.navigation.AuthScreen
import com.ummah.mosque.app.navigation.HomeScreen
import com.ummah.mosque.common.storage.LocalStorageManager
import com.ummah.mosque.common.utils.default
import com.ummah.mosque.events.AppEvents
import com.ummah.mosque.features.splash.domain.GetLocalizationUseCase
import com.ummah.mosque.features.splash.domain.GetLocationMapUseCase
import com.ummah.mosque.firebase.FirebaseAnalyticsManager
import com.ummah.mosque.firebase.RemoteConfigurationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val remoteConfigurationManager: RemoteConfigurationManager,
    private val getLocationMapUseCase: GetLocationMapUseCase,
    private val localStorageManager: LocalStorageManager,
    private val getLocalizationUseCase: GetLocalizationUseCase,
    private val firebaseAnalyticsManager: FirebaseAnalyticsManager
) : ViewModel() {

    private companion object {
        private const val LOCATION_MAP = "location_map"
        private const val LOCALIZATION = "localization"
    }

    private val _nextDestination by lazy { MutableStateFlow<String?>(null) }
    val nextDestination: StateFlow<String?>
        get() = _nextDestination

    init {
        activateRemoteConfig()
    }

    private fun activateRemoteConfig() {
        viewModelScope.launch {
            remoteConfigurationManager.activate()
            fetchCountriesAndCities()
        }
    }

    private fun fetchLocalization() {
        viewModelScope.launch {
            val localization = getLocalizationUseCase()
            if (localization.isNotBlank()) {
                firebaseAnalyticsManager.logStringEvent(AppEvents.LocalisationUpdated.toString())
                localStorageManager.putString(LOCALIZATION, localization)
                navigateToNextDestination()
            }
        }
    }

    private fun navigateToNextDestination() {
        viewModelScope.launch {
            localStorageManager.isLoggedIn().collectLatest { isLoggedIn ->
                if (isLoggedIn.default()) {
                    _nextDestination.value = HomeScreen.Home.routeId
                } else {
                    _nextDestination.value = AuthScreen.Login.routeId
                }
            }
        }
    }

    private fun fetchCountriesAndCities() {
        viewModelScope.launch {
            val locationMap = getLocationMapUseCase()
            if (locationMap.isNotEmpty()) {
                firebaseAnalyticsManager.logStringEvent(AppEvents.CountriesFetched.toString())
                localStorageManager.putString(LOCATION_MAP, locationMap)
                fetchLocalization()
            }
        }
    }
}