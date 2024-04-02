package com.ovais.mosqueapp.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovais.mosqueapp.common.ToastManager
import com.ovais.mosqueapp.common.storage.KEY_IS_LOGGED_IN
import com.ovais.mosqueapp.common.storage.KEY_USER_ID
import com.ovais.mosqueapp.common.storage.LocalStorageManager
import com.ovais.mosqueapp.features.login.data.AuthenticationResults
import com.ovais.mosqueapp.features.login.data.UserData
import com.ovais.mosqueapp.features.login.domain.LoginAuthenticationUseCase
import com.ovais.mosqueapp.features.login.domain.PasswordValidationResults
import com.ovais.mosqueapp.features.login.domain.PasswordValidationUseCase
import com.ovais.mosqueapp.features.login.domain.UsernameValidationResults
import com.ovais.mosqueapp.features.login.domain.UsernameValidationUseCase
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
    private val localStorageManager: LocalStorageManager
) : ViewModel() {
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
                    createSession(results.data.userId)
                }

                is AuthenticationResults.Failure -> {
                    _isLoading.value = false
                    showToast(results.message)
                }
            }
        }
    }

    private fun createSession(userId: String) {
        viewModelScope.launch {
            localStorageManager.putBoolean(KEY_IS_LOGGED_IN, true)
            localStorageManager.putString(KEY_USER_ID, userId)
            _isLoading.value = false
            _isLoginSuccess.value = true
        }

    }

    private fun showToast(message: String) {
        toastManager.show(message)
    }
}