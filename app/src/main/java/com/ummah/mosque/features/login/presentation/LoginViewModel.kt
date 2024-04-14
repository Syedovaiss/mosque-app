package com.ummah.mosque.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummah.mosque.common.PermissionManager
import com.ummah.mosque.common.ToastManager
import com.ummah.mosque.common.storage.KEY_CITY
import com.ummah.mosque.common.storage.KEY_COUNTRY
import com.ummah.mosque.common.storage.KEY_IS_LOGGED_IN
import com.ummah.mosque.common.storage.KEY_PHONE_NUMBER
import com.ummah.mosque.common.storage.KEY_USERNAME
import com.ummah.mosque.common.storage.KEY_USER_ID
import com.ummah.mosque.common.storage.LocalStorageManager
import com.ummah.mosque.features.login.data.AuthenticationResults
import com.ummah.mosque.features.login.data.UserData
import com.ummah.mosque.features.login.domain.LoginAuthenticationUseCase
import com.ummah.mosque.features.login.domain.PasswordValidationResults
import com.ummah.mosque.features.login.domain.PasswordValidationUseCase
import com.ummah.mosque.features.login.domain.UsernameValidationResults
import com.ummah.mosque.features.login.domain.UsernameValidationUseCase
import com.ummah.mosque.features.register.data.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usernameValidationUseCase: UsernameValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val toastManager: ToastManager,
    private val loginAuthenticationUseCase: LoginAuthenticationUseCase,
    private val localStorageManager: LocalStorageManager,
    private val permissionManager: PermissionManager
) : ViewModel() {
    private val _canRequestLocationPermissions by lazy {
        MutableStateFlow(false)
    }
    val canRequestLocationPermissions: StateFlow<Boolean>
        get() = _canRequestLocationPermissions

    private val _isLoading by lazy {
        MutableStateFlow(false)
    }
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _isLoginSuccess by lazy { MutableStateFlow(false) }
    val isLoginSuccess: StateFlow<Boolean>
        get() = _isLoginSuccess

    fun onLogin(username: String, password: String) {
        when {
            usernameValidationUseCase(username) !is UsernameValidationResults.Valid -> {
                showToast("Invalid Username")
            }

            passwordValidationUseCase(password) !is PasswordValidationResults.Valid -> {
                showToast("Invalid Password")
            }

            else -> onSignIn(username, password)
        }
    }

    private fun onSignIn(username: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val results = loginAuthenticationUseCase(UserData(username, password))) {
                is AuthenticationResults.Success -> {
                    saveUserInfo(results.data)
                }

                is AuthenticationResults.Failure -> {
                    _isLoading.value = false
                    showToast(results.message)
                }
            }
        }
    }

    private fun saveUserInfo(info: UserInfo) {
        viewModelScope.launch {
            localStorageManager.putString(KEY_USERNAME,info.username)
            localStorageManager.putString(KEY_PHONE_NUMBER,info.phoneNumber)
            localStorageManager.putString(KEY_CITY,info.city)
            localStorageManager.putString(KEY_COUNTRY,info.country)
            createSession(info.userId)
        }
    }

    private fun createSession(userId: String) {
        viewModelScope.launch {
            localStorageManager.putBoolean(KEY_IS_LOGGED_IN, true)
            localStorageManager.putString(KEY_USER_ID, userId)
            _isLoading.value = false
            validateLocationPermissions()
        }

    }

    private fun validateLocationPermissions() {
        if (permissionManager.isLocationAllowed) {
            _isLoginSuccess.value = true
        } else {
            requestPermissions()
        }
    }
    fun onLocationPermissionGranted() {
        _isLoginSuccess.value = true
    }

    private fun requestPermissions() {
        _canRequestLocationPermissions.value = true
    }

    private fun showToast(message: String) {
        toastManager.show(message)
    }
}