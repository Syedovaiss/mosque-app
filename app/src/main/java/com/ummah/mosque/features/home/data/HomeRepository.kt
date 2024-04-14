package com.ummah.mosque.features.home.data

import com.ummah.mosque.firebase.FirebaseStorageManager
import com.ummah.mosque.firebase.data.CarouselResult
import javax.inject.Inject

interface HomeRepository {
    suspend fun getCarouselImages(): CarouselResult
}

class DefaultHomeRepository @Inject constructor(
    private val firebaseStorageManager: FirebaseStorageManager
) : HomeRepository {

    override suspend fun getCarouselImages(): CarouselResult{
        return firebaseStorageManager.fetchCarouselImages()
    }
}