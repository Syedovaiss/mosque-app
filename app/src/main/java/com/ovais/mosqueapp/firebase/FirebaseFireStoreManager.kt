package com.ovais.mosqueapp.firebase

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ovais.mosqueapp.common.dto.QueryResult
import com.ovais.mosqueapp.common.utils.completeAsQueryResult
import javax.inject.Inject

interface FirebaseFireStoreManager {
    suspend fun insertUsernames(username: String): QueryResult<DocumentReference>
    suspend fun isUsernameAvailable(username: String): QueryResult<Boolean>
}

class DefaultFirebaseFireStoreManager @Inject constructor() : FirebaseFireStoreManager {

    private companion object {
        private const val COLLECTION_USERNAMES = "usernames"
        private const val KEY_USERNAME = "username"
    }

    private val database by lazy {
        Firebase.firestore
    }

    override suspend fun insertUsernames(username: String): QueryResult<DocumentReference> {
        return database.collection(COLLECTION_USERNAMES).add(mapOf(KEY_USERNAME to username)).completeAsQueryResult()
    }

    override suspend fun isUsernameAvailable(username: String): QueryResult<Boolean> {
        val queryReference = database.collection(COLLECTION_USERNAMES).get().completeAsQueryResult()
       return when (queryReference) {
            is QueryResult.Success -> {
                if (queryReference.data.isEmpty) {
                    QueryResult.Success(true)
                }
                else if (queryReference.data.documents.any { it.data?.get(KEY_USERNAME) != username }) {
                    QueryResult.Success(true)
                } else {
                    QueryResult.Failed("Username not available!")
                }
            }

            is QueryResult.Failed -> {
                return QueryResult.Failed(queryReference.message)
            }
        }
    }
}