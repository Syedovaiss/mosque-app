package com.ummah.mosque.features.passwordreset.domain

import com.ummah.mosque.common.dto.QueryResult
import com.ummah.mosque.firebase.FirebaseRealTimeDatabaseManager
import javax.inject.Inject

interface PasswordUpdateRepository {
    suspend fun updatePassword(phoneNumber: String, password: String): PasswordResetResults
}

class DefaultPasswordUpdateRepository @Inject constructor(
    private val firebaseRealTimeDatabaseManager: FirebaseRealTimeDatabaseManager
) : PasswordUpdateRepository {
    override suspend fun updatePassword(
        phoneNumber: String,
        password: String
    ): PasswordResetResults {
        return when (
            val userInfoResult = firebaseRealTimeDatabaseManager.getUserByPhoneNumber(phoneNumber)
        ) {
            is QueryResult.Success -> {
                val result = firebaseRealTimeDatabaseManager.updatePassword(
                    userId = userInfoResult.data.userId,
                    password = password
                )
                when(result) {
                    is QueryResult.Success -> {
                        PasswordResetResults.Success(userInfoResult.data.userId)
                    }
                    is QueryResult.Failed -> {
                        PasswordResetResults.Failure(result.message)
                    }
                }
            }

            is QueryResult.Failed -> PasswordResetResults.Failure(userInfoResult.message)
        }
    }
}