package com.ummah.mosque.features.codeverification.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthProvider
import com.ummah.mosque.app.navigation.ARGS_DATA
import com.ummah.mosque.common.ParsingService
import com.ummah.mosque.common.utils.default
import com.ummah.mosque.features.forgotpassword.data.ForgotPasswordData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CodeVerificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val parsingService: ParsingService
) : ViewModel() {
    private val _isLoading by lazy {
        MutableStateFlow(false)
    }
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val arguments: ForgotPasswordData? by lazy {
        savedStateHandle.get<String?>(ARGS_DATA)?.let {
            parsingService.decodeFromString<ForgotPasswordData>(
                it
            )
        }
    }
    private val _onCodeVerificationSuccess by lazy {
        MutableStateFlow<String?>(null)
    }
    val onCodeVerificationSuccess: StateFlow<String?>
        get() = _onCodeVerificationSuccess

    fun onVerify(otpText: String) {
        arguments?.let { args ->
            val credential = PhoneAuthProvider.getCredential(args.verificationId.default(), otpText)
            _onCodeVerificationSuccess.value = if (credential.smsCode == otpText) {
                arguments?.phoneNumber.default()
            } else {
                null
            }
        }

    }
}