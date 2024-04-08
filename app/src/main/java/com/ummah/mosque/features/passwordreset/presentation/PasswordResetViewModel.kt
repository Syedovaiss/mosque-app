package com.ummah.mosque.features.passwordreset.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ummah.mosque.app.navigation.ARGS_DATA
import com.ummah.mosque.common.ToastManager
import com.ummah.mosque.features.passwordreset.data.PasswordResetData
import com.ummah.mosque.features.passwordreset.domain.PasswordResetResults
import com.ummah.mosque.features.passwordreset.domain.PasswordUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordResetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val passwordUpdateUseCase: PasswordUpdateUseCase,
    private val toastManager: ToastManager
) : ViewModel() {
    private val _isLoading by lazy {
        MutableStateFlow(false)
    }
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _isPasswordResetSuccess by lazy {
        MutableStateFlow(false)
    }
    val isPasswordResetSuccess: StateFlow<Boolean>
        get() = _isPasswordResetSuccess


    private val phoneNumber: String? by lazy {
        savedStateHandle.get<String?>(ARGS_DATA)
    }

    fun resetPassword(password: String, retypedPassword: String) {
        when {
            password.isEmpty() -> {
                showToast("Password can't be empty!")
            }

            retypedPassword.isEmpty() -> {
                showToast("Retyped password can't be empty!")
            }

            (password == retypedPassword).not() -> {
                showToast("Password Mismatch!")
            }

            else -> {
                _isLoading.value = true
                updatePassword(password)
            }
        }

    }

    private fun updatePassword(password: String) {
        viewModelScope.launch {
            phoneNumber?.let { number ->
                val result = passwordUpdateUseCase(
                    PasswordResetData(
                        phoneNumber = number,
                        password = password
                    )
                )
                when (result) {
                    is PasswordResetResults.Success -> {
                        _isLoading.value = false
                        _isPasswordResetSuccess.value = true
                    }

                    is PasswordResetResults.Failure -> {
                        _isLoading.value = false
                        showToast(result.message)
                    }
                }
            } ?: run {
                showToast("Something went wrong!")
            }

        }
    }

    private fun showToast(message: String) {
        toastManager.show(message)
    }

}