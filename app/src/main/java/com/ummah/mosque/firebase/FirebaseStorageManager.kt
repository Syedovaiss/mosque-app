package com.ummah.mosque.firebase

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.ummah.mosque.common.ScopeProvider
import com.ummah.mosque.common.dto.QueryResult
import com.ummah.mosque.common.utils.completeAsQueryResult
import com.ummah.mosque.firebase.data.CarouselResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

interface FirebaseStorageManager {
    suspend fun fetchCarouselImages(): CarouselResult
}

class DefaultFirebaseStorageManager @Inject constructor(
    private val scopeProvider: ScopeProvider
) : FirebaseStorageManager {

    private companion object {
        private const val CAROUSEL_PATH = "carousel/"
    }

    private val storage by lazy {
        FirebaseStorage.getInstance().reference
    }

    override suspend fun fetchCarouselImages(): CarouselResult {
        return suspendCancellableCoroutine { continuation ->
            val folderReference = storage.child(CAROUSEL_PATH)
            val imageUrls = mutableListOf<String>()
            scopeProvider.background.launch {
                when (val folderResult = folderReference.listAll().completeAsQueryResult()) {
                    is QueryResult.Success -> {
                        folderResult.data.items.forEach { ref ->
                            when (val imageUrlResult = ref.downloadUrl.completeAsQueryResult()) {
                                is QueryResult.Success -> {
                                    imageUrls.add(imageUrlResult.data.toString())
                                }

                                is QueryResult.Failed -> {
                                    Log.i("data---", imageUrlResult.message)
                                    continuation.resume(CarouselResult.Failure(imageUrlResult.message))
                                }
                            }
                        }
                        continuation.resume(CarouselResult.Success(imageUrls))
                    }

                    is QueryResult.Failed -> {
                        Log.i("data---", folderResult.message)
                        continuation.resume(CarouselResult.Failure(folderResult.message))
                    }
                }
            }
        }
    }
}