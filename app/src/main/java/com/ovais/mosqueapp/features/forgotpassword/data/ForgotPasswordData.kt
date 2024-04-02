package com.ovais.mosqueapp.features.forgotpassword.data

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordData(
    val phoneNumber: String,
    val verificationId: String
)
