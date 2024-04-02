package com.ovais.mosqueapp.features.forgotpassword.presentation

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ovais.mosqueapp.common.ParsingService
import com.ovais.mosqueapp.common.ToastManager
import com.ovais.mosqueapp.common.encodeToString
import com.ovais.mosqueapp.features.forgotpassword.data.ForgotPasswordData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val toastManager: ToastManager
) : ViewModel() {

    private val _isLoading by lazy {
        MutableStateFlow(false)
    }
    val isLoading: StateFlow<Boolean>
        get() = _isLoading
    private val _canNavigateForVerification by lazy {
        MutableStateFlow<String?>(null)
    }
    val canNavigateForVerification: StateFlow<String?>
        get() = _canNavigateForVerification

    fun sendVerificationCode(phoneNumber: String, activity: ComponentActivity?) {
        activity?.let {
            _isLoading.value = true
            sendOTP(phoneNumber, it)
        } ?: run {
            showToast("Something went wrong!")
        }
    }

    private fun sendOTP(phoneNumber: String, activity: Activity) {
        if (phoneNumber.contains("+").not()) {
            showToast("Please enter a valid phone number")
        } else {
            val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(getValidPhoneNumber(phoneNumber))
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

                    override fun onVerificationFailed(e: FirebaseException) {
                        _isLoading.value = false
                        showToast(e.message.toString())
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        _isLoading.value = false
                        showToast("Code sent successfully!")
                        val data = ForgotPasswordData(
                            phoneNumber = phoneNumber,
                            verificationId = verificationId
                        )
                        _canNavigateForVerification.value = encodeToString<ForgotPasswordData>(data)
                    }
                }).build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun showToast(message: String) {
        toastManager.show(message)
    }

    private fun getValidPhoneNumber(phoneNumber: String): String {
        return if (phoneNumber.startsWith("03")) {
            phoneNumber.replaceFirst("0", "+92")
        } else {
            phoneNumber
        }
    }
}