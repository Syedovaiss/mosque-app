package com.ovais.mosqueapp.features.login.domain

import com.ovais.mosqueapp.common.SuspendParameterizedUseCase
import com.ovais.mosqueapp.features.login.data.AuthenticationResults
import com.ovais.mosqueapp.features.login.data.UserData
import com.ovais.mosqueapp.firebase.FirebaseRealTimeDatabaseManager
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