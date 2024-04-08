package com.ummah.mosque.features.register.data

import com.ummah.mosque.common.dto.QueryResult
import com.ummah.mosque.firebase.FirebaseFireStoreManager
import com.ummah.mosque.firebase.FirebaseRealTimeDatabaseManager
import java.util.HashMap
import javax.inject.Inject

interface RegistrationRepository {
    suspend fun insertUsername(username: String): QueryResult<Boolean>
    suspend fun isUsernameAvailable(username: String): QueryResult<Boolean>
    suspend fun registerUser(userInfo: HashMap<Any,Any>): QueryResult<Boolean>
}

class DefaultRegistrationRepository @Inject constructor(
    private val fireStoreManager: FirebaseFireStoreManager,
    private val firebaseRealTimeDatabaseManager: FirebaseRealTimeDatabaseManager
) : RegistrationRepository {
    override suspend fun insertUsername(username: String): QueryResult<Boolean> {
        return when(val data  = fireStoreManager.insertUsernames(username)) {
            is QueryResult.Success -> {
                QueryResult.Success(true)
            }
            is QueryResult.Failed -> {
                QueryResult.Failed(data.message)
            }
        }
    }
    override suspend fun isUsernameAvailable(username: String): QueryResult<Boolean> {
       return fireStoreManager.isUsernameAvailable(username)
    }

    override suspend fun registerUser(userInfo: HashMap<Any,Any>): QueryResult<Boolean> {
        return firebaseRealTimeDatabaseManager.insertUser(userInfo)
    }
}