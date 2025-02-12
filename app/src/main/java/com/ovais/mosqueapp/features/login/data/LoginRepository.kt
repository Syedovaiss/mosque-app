package com.ovais.mosqueapp.features.login.data

import com.ovais.mosqueapp.firebase.FirebaseRealTimeDatabaseManager
import javax.inject.Inject

interface LoginRepository {
    suspend fun authenticateUser(
        username: String,
        password: String
    ): AuthenticationResults
}

class DefaultLoginRepository @Inject constructor(
    private val firebaseRealTimeDatabaseManager: FirebaseRealTimeDatabaseManager
) : LoginRepository {

    override suspend fun authenticateUser(
        username: String,
        password: String
    ): AuthenticationResults {
        return firebaseRealTimeDatabaseManager.authenticate(username, password)
    }
}