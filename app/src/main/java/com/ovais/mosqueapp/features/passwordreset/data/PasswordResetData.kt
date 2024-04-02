package com.ovais.mosqueapp.features.passwordreset.data

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetData(
    val phoneNumber: String,
    val password: String
)
