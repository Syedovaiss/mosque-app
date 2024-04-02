package com.ovais.mosqueapp.features.codeverification.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthProvider
import com.ovais.mosqueapp.app.navigation.ARGS_DATA
import com.ovais.mosqueapp.common.ParsingService
import com.ovais.mosqueapp.common.utils.default
import com.ovais.mosqueapp.features.forgotpassword.data.ForgotPasswordData
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