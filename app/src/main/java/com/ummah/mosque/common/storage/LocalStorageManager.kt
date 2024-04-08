package com.ummah.mosque.common.storage

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"
const val KEY_USER_ID = "USER_ID"

interface LocalStorageManager {
    suspend fun isLoggedIn(): Flow<Boolean?>
    suspend fun putString(key: String, value: String)
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getString(key: String): Flow<String?>
}

class DefaultLocalStorageManager @Inject constructor(
    private val storageManager: StorageManager
) : LocalStorageManager {

    override suspend fun isLoggedIn(): Flow<Boolean?> {
        return storageManager.getBoolean(KEY_IS_LOGGED_IN)
    }

    override suspend fun getString(key: String): Flow<String?> {
        return storageManager.getString(key)
    }

    override suspend fun putString(key: String, value: String) {
        storageManager.putString(key, value)
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        storageManager.putBoolean(key, value)
    }
}