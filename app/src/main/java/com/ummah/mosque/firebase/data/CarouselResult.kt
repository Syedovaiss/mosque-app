package com.ummah.mosque.firebase.data

sealed interface CarouselResult {
    data class Success(val images: List<String>) : CarouselResult
    data class Failure(val message: String) : CarouselResult
}