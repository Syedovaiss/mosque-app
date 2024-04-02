package com.ovais.mosqueapp.features.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovais.mosqueapp.common.ToastManager
import com.ovais.mosqueapp.common.dto.ValidationResults
import com.ovais.mosqueapp.common.storage.LocalStorageManager
import com.ovais.mosqueapp.common.utils.EMPTY_STRING
import com.ovais.mosqueapp.common.utils.default
import com.ovais.mosqueapp.features.register.data.City
import com.ovais.mosqueapp.features.register.data.CityData
import com.ovais.mosqueapp.features.register.data.Country
import com.ovais.mosqueapp.features.register.data.UserInfo
import com.ovais.mosqueapp.features.register.data.UserInfoUiData
import com.ovais.mosqueapp.features.register.domain.AddUsernameResults
import com.ovais.mosqueapp.features.register.domain.AddUsernameUseCase
import com.ovais.mosqueapp.features.register.domain.GetCitiesUseCase
import com.ovais.mosqueapp.features.register.domain.GetCountriesUseCase
import com.ovais.mosqueapp.features.register.domain.RegisterUserUseCase
import com.ovais.mosqueapp.features.register.domain.RegistrationResults
import com.ovais.mosqueapp.features.register.domain.RegistrationValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val localStorageManager: LocalStorageManager,
    private val registrationValidationUseCase: RegistrationValidationUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val addUsernameUseCase: AddUsernameUseCase,
    private val toastManager: ToastManager
) : ViewModel() {
    private companion object {
        private const val LOCATION_MAP = "location_map"
    }

    private val countries by lazy { arrayListOf<Country>() }
    private val cities by lazy { arrayListOf<City>() }

    private val _countriesUiData by lazy {
        MutableStateFlow<List<String>>(listOf())
    }
    val countriesUiData: StateFlow<List<String>>
        get() = _countriesUiData
    private val _citiesUiData by lazy {
        MutableStateFlow<List<String>>(listOf())
    }
    val citiesUiData: StateFlow<List<String>>
        get() = _citiesUiData
    private val _isRegisteredSuccessfully by lazy { MutableStateFlow(false) }
    val isRegisteredSuccessfully: StateFlow<Boolean>
        get() = _isRegisteredSuccessfully

    private val _isLoading by lazy {
        MutableStateFlow(false)
    }
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            localStorageManager.getString(LOCATION_MAP).collectLatest { locations ->
                locations?.let { location ->
                    countries.clear()
                    countries.addAll(getCountriesUseCase(location))
                    _countriesUiData.value = countries.map { country ->
                        country.name
                    }
                    getCities(countries.first().id)
                }
            }
        }
    }

    private fun getCities(countryId: Long) {
        viewModelScope.launch {
            localStorageManager.getString(LOCATION_MAP).collectLatest { locations ->
                locations?.let {
                    cities.clear()
                    cities.addAll(
                        getCitiesUseCase(
                            param = CityData(
                                locations = it,
                                countryId = countryId
                            )
                        )
                    )
                    _citiesUiData.value = cities.map { it.name }
                }
            }
        }
    }

    fun onSignUp(
        username: String,
        password: String,
        phoneNumber: String,
        country: String,
        city: String
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = registrationValidationUseCase(
                UserInfoUiData(
                    username,
                    password,
                    country.ifBlank { countries.first().name },
                    city.ifBlank { cities.first().name },
                    phoneNumber
                )
            )
            when (result) {
                is ValidationResults.Failure -> {
                    _isLoading.value = false
                    showToast(
                        message = result.message
                    )
                }

                is ValidationResults.Success -> {
                    addUsername(username, password, phoneNumber, country, city)
                }
            }
        }
    }

    private fun addUsername(
        username: String,
        password: String,
        phoneNumber: String,
        country: String,
        city: String
    ) {
        viewModelScope.launch {
            when (val result = addUsernameUseCase(username)) {
                is AddUsernameResults.Success -> {
                    registerUser(username, password, phoneNumber, country, city)
                }

                is AddUsernameResults.Failure -> {
                    _isLoading.value = false
                    showToast(result.message)
                }
            }
        }
    }

    private fun registerUser(
        username: String,
        password: String,
        phoneNumber: String,
        country: String,
        city: String
    ) {
        viewModelScope.launch {
            val countryId =
                countries.find { it.name == country.ifBlank { countries.first().name } }?.id
            val cityId = cities.find { it.name == city.ifBlank { cities.first().name } }?.id
            val countryCode =
                countries.find { it.name == country.ifBlank { countries.first().name } }?.code
            val result = registerUserUseCase(
                UserInfo(
                    username = username,
                    password = password,
                    country = country.ifBlank { countries.first().name },
                    city = city.ifBlank { cities.first().name },
                    phoneNumber = getFormattedPhoneNumber(phoneNumber, countryCode),
                    countryId = countryId.default(),
                    cityId = cityId.default()
                )
            )
            when (result) {
                is RegistrationResults.Success -> {
                    _isLoading.value = false
                    _isRegisteredSuccessfully.value = true
                }

                is RegistrationResults.Failure -> {
                    _isLoading.value = false
                    showToast(
                        message = result.message
                    )
                }
            }
        }
    }

    fun fetchCitiesByCountry(name: String) {
        val id = countries.find { it.name == name }?.id.default()
        getCities(id)
    }

    private fun showToast(message: String = EMPTY_STRING) {
        toastManager.show(message)
    }

    private fun getFormattedPhoneNumber(phoneNumber: String, countryCode: String?): String {
        return if (phoneNumber.startsWith(countryCode.default())) {
            return phoneNumber
        } else {
            phoneNumber.replaceFirst("0", countryCode.default())
        }
    }

}