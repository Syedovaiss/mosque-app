package com.ummah.mosque.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummah.mosque.common.ToastManager
import com.ummah.mosque.features.home.data.LocationInfo
import com.ummah.mosque.features.home.domain.GetCarouselImagesUseCase
import com.ummah.mosque.features.home.domain.GetCurrentLocationUseCase
import com.ummah.mosque.features.home.domain.GetMapSettingsUseCase
import com.ummah.mosque.features.home.domain.LocationResults
import com.ummah.mosque.features.home.domain.MapSettings
import com.ummah.mosque.features.home.domain.MosqueMapSettingsData
import com.ummah.mosque.firebase.data.CarouselResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val mapSettingUseCase: GetMapSettingsUseCase,
    private val toastManager: ToastManager,
    private val getCarouselImagesUseCase: GetCarouselImagesUseCase
) : ViewModel() {
    private val _isLoading by lazy { MutableStateFlow(true) }
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _mapSettings by lazy {
        MutableStateFlow<MosqueMapSettingsData?>(null)
    }
    val mapSettings: StateFlow<MosqueMapSettingsData?>
        get() = _mapSettings
    private val _usersLocation by lazy {
        MutableStateFlow<LocationInfo?>(null)
    }
    val usersLocation: StateFlow<LocationInfo?>
        get() = _usersLocation

    private val _carouselItems by lazy { MutableStateFlow<List<String>>(emptyList()) }
    val carouselItems: StateFlow<List<String>>
        get() = _carouselItems

    private val _mosqueItems by lazy { MutableStateFlow<List<String>>(emptyList()) }
    val mosqueItems: StateFlow<List<String>>
        get() = _mosqueItems

    init {
        fetchCarouselImages()
    }

    private fun fetchCarouselImages() {
        viewModelScope.launch {
            when (val result = getCarouselImagesUseCase()) {
                is CarouselResult.Success -> {
                    _carouselItems.value = result.images
                    fetchAvailableMosque()
                }

                is CarouselResult.Failure -> {
                    _carouselItems.value = emptyList()
                    fetchAvailableMosque()
                }
            }
        }
    }
    private fun fetchAvailableMosque() {
        _mosqueItems.value = emptyList()
        _isLoading.update { false }
    }

    /*  private fun initMapSettings() {
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
                     _usersLocation.value = LocationInfo(
                         lat = result.latLng.latitude,
                         lng = result.latLng.longitude
                     )
                 }

                 is LocationResults.Failure -> {
                     toastManager.show(result.error)
                 }
             }
         }
     }*/

}