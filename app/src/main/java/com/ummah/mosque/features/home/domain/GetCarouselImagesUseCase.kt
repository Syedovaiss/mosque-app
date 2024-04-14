package com.ummah.mosque.features.home.domain

import com.ummah.mosque.common.SuspendUseCase
import com.ummah.mosque.features.home.data.HomeRepository
import com.ummah.mosque.firebase.data.CarouselResult
import javax.inject.Inject

interface GetCarouselImagesUseCase: SuspendUseCase<CarouselResult>

class DefaultGetCarouselImagesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
): GetCarouselImagesUseCase {
    override suspend fun invoke(): CarouselResult {
        return homeRepository.getCarouselImages()
    }
}