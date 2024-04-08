package com.ummah.mosque.features.login.data

import com.ummah.mosque.features.register.data.UserInfo

sealed interface AuthenticationResults {
    data class Success(val data: UserInfo) : AuthenticationResults
    data class Failure(val message: String) : AuthenticationResults
}