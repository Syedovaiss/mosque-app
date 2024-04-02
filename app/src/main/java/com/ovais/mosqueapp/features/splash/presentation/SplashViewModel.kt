package com.ovais.mosqueapp.features.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovais.mosqueapp.app.navigation.Screens
import com.ovais.mosqueapp.common.storage.LocalStorageManager
import com.ovais.mosqueapp.common.utils.default
import com.ovais.mosqueapp.events.AppEvents
import com.ovais.mosqueapp.features.splash.domain.GetLocalizationUseCase
import com.ovais.mosqueapp.features.splash.domain.GetLocationMapUseCase
import com.ovais.mosqueapp.firebase.FirebaseAnalyticsManager
import com.ovais.mosqueapp.firebase.RemoteConfigurationManager
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
                    _nextDestination.value = Screens.Home.routeId
                } else {
                    _nextDestination.value = Screens.Login.routeId
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