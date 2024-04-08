package com.ummah.mosque.features.login.domain

import com.ummah.mosque.common.SuspendParameterizedUseCase
import com.ummah.mosque.features.login.data.AuthenticationResults
import com.ummah.mosque.features.login.data.UserData
import com.ummah.mosque.firebase.FirebaseRealTimeDatabaseManager
import javax.inject.Inject

interface LoginAuthenticationUseCase : SuspendParameterizedUseCase<UserData, AuthenticationResults>
class DefaultLoginAuthenticationUseCase @Inject constructor(
    private val firebaseRealTimeDatabaseManager: FirebaseRealTimeDatabaseManager
) : LoginAuthenticationUseCase {
    override suspend fun invoke(param: UserData): AuthenticationResults {
        return firebaseRealTimeDatabaseManager.authenticate(
            username = param.username,
            password = param.password
        )
    }
}