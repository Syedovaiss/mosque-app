package com.ovais.mosqueapp.features.login.data

import com.ovais.mosqueapp.features.register.data.UserInfo

sealed interface AuthenticationResults {
    data class Success(val data: UserInfo) : AuthenticationResults
    data class Failure(val message: String) : AuthenticationResults
}