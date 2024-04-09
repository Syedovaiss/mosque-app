package com.ummah.mosque.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummah.mosque.common.ToastManager
import com.ummah.mosque.features.home.data.LocationInfo
import com.ummah.mosque.features.home.domain.GetCurrentLocationUseCase
import com.ummah.mosque.features.home.domain.GetMapSettingsUseCase
import com.ummah.mosque.features.home.domain.LocationResults
import com.ummah.mosque.features.home.domain.MapSettings
import com.ummah.mosque.features.home.domain.MosqueMapSettingsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val mapSettingUseCase: GetMapSettingsUseCase,
    private val toastManager: ToastManager
) : ViewModel() {
    private val _mapSettings by lazy {
        MutableStateFlow<MosqueMapSettingsData?>(null)
    }
    val mapSettings: StateFlow<MosqueMapSettingsData?>
        get() = _mapSettings
    private val _currentLocation by lazy {
        MutableStateFlow<LocationInfo?>(null)
    }
    val currentLocation: StateFlow<LocationInfo?>
        get() = _currentLocation

    init {
        initMapSettings()
        fetchCurrentLocation()
    }

    private fun initMapSettings() {
        when (val result = mapSettingUseCase()) {
            is MapSettings.Success -> {
                _mapSettings.value = result.settings
            }

            is MapSettings.Failure -> {
                toastManager.show(result.error)
            }
        }
    }

    private fun fetchCurrentLocation() {
        viewModelScope.launch {
            when (val result = getCurrentLocationUseCase()) {
                is LocationResults.Success -> {
                    _currentLocation.value = LocationInfo(
                        lat = result.latLng.latitude,
                        lng = result.latLng.longitude
                    )
                }

                is LocationResults.Failure -> {
                    toastManager.show(result.error)
                }
            }
        }
    }

}