package com.ovais.mosqueapp.features.register.data

import com.ovais.mosqueapp.common.dto.QueryResult
import com.ovais.mosqueapp.firebase.FirebaseFireStoreManager
import com.ovais.mosqueapp.firebase.FirebaseRealTimeDatabaseManager
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