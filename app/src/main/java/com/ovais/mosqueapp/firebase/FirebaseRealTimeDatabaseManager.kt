package com.ovais.mosqueapp.firebase

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ovais.mosqueapp.common.dto.QueryResult
import com.ovais.mosqueapp.common.utils.completeAsQueryResult
import com.ovais.mosqueapp.features.login.data.AuthenticationResults
import com.ovais.mosqueapp.features.register.data.UserInfo
import javax.inject.Inject

interface FirebaseRealTimeDatabaseManager {
    suspend fun insertUser(data: HashMap<Any, Any>): QueryResult<Boolean>
    suspend fun authenticate(username: String, password: String): AuthenticationResults
    suspend fun getUserByPhoneNumber(phoneNumber: String): QueryResult<UserInfo>
    suspend fun updatePassword(userId: String, password: String): QueryResult<Unit>
}

class DefaultFirebaseRealTimeDatabaseManager @Inject constructor() :
    FirebaseRealTimeDatabaseManager {
    private companion object {
        private const val USER_ID = "userId"
        private const val USER_COLLECTION = "users"
        private const val PHONE_NUMBER = "phoneNumber"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
    }

    private val database by lazy {
        Firebase.database
    }

    override suspend fun insertUser(
        data: HashMap<Any, Any>
    ): QueryResult<Boolean> {
        return try {
            if (isUserAlreadyExists(data[PHONE_NUMBER].toString())) {
                QueryResult.Failed("User already exists!")
            } else {
                addUser(data)
            }
        } catch (e: Exception) {
            QueryResult.Failed(e.message.toString())
        }
    }

    private suspend fun addUser(data: HashMap<Any, Any>): QueryResult<Boolean> {
        val result = database
            .getReference(USER_COLLECTION)
            .child(data[USER_ID].toString())
            .setValue(data).completeAsQueryResult()
        return when (result) {
            is QueryResult.Success -> {
                QueryResult.Success(true)
            }

            is QueryResult.Failed -> {
                QueryResult.Failed(result.message)
            }
        }
    }

    private suspend fun isUserAlreadyExists(phoneNumber: String): Boolean {
        when (val result = database.getReference(USER_COLLECTION).get().completeAsQueryResult()) {
            is QueryResult.Success -> {
                val isExists =
                    result.data.children.any { (it.value as HashMap<*, *>)[PHONE_NUMBER] == phoneNumber }
                return isExists
            }

            else -> return true
        }
    }

    override suspend fun authenticate(username: String, password: String): AuthenticationResults {
        return when (val result =
            database.getReference(USER_COLLECTION).get().completeAsQueryResult()) {
            is QueryResult.Success -> {
                val user =
                    result.data.children.find { (it.value as HashMap<*, *>)[KEY_USERNAME] == username }
                user?.let { snapshot ->
                    if ((snapshot.value as HashMap<*, *>)[KEY_PASSWORD] == password) {
                        AuthenticationResults.Success(UserInfo.fromHashMap((snapshot.value as HashMap<*, *>)))
                    } else {
                        AuthenticationResults.Failure("Invalid Password")
                    }
                } ?: run {
                    AuthenticationResults.Failure("Invalid Username")
                }

            }

            is QueryResult.Failed -> AuthenticationResults.Failure(result.message)
        }
    }

    override suspend fun getUserByPhoneNumber(phoneNumber: String): QueryResult<UserInfo> {
        return when (val result =
            database.getReference(USER_COLLECTION).get().completeAsQueryResult()) {
            is QueryResult.Success -> {
                val user =
                    result.data.children.find { (it.value as HashMap<*, *>)[PHONE_NUMBER] == phoneNumber }
                user?.let { snapshot ->
                    QueryResult.Success(UserInfo.fromHashMap((snapshot.value as HashMap<*, *>)))
                } ?: run {
                    QueryResult.Failed("No User found with this phone number!")
                }
            }

            is QueryResult.Failed -> QueryResult.Failed(result.message)
        }
    }

    override suspend fun updatePassword(userId: String, password: String): QueryResult<Unit> {
        val result = database
            .getReference(USER_COLLECTION)
            .child(userId)
            .child(KEY_PASSWORD)
            .setValue(password)
            .completeAsQueryResult()
        return when (result) {
            is QueryResult.Success -> {
                QueryResult.Success(Unit)
            }

            is QueryResult.Failed -> {
                QueryResult.Failed(result.message)
            }
        }
    }
}