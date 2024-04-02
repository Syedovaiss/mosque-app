package com.ovais.mosqueapp.features.splash.domain

import com.ovais.mosqueapp.common.LocaleCode
import com.ovais.mosqueapp.common.LocaleManager
import com.ovais.mosqueapp.common.UseCase
import com.ovais.mosqueapp.common.utils.EMPTY_STRING
import com.ovais.mosqueapp.firebase.RemoteConfigurationManager
import javax.inject.Inject

interface GetLocalizationUseCase : UseCase<String>

class DefaultGetLocalizationUseCase @Inject constructor(
    private val remoteConfigurationManager: RemoteConfigurationManager,
    private val localeManager: LocaleManager
) : GetLocalizationUseCase {

    private companion object {
        private const val KEY_EN_LOCALIZATION = "en_app_localization"
        private const val KEY_UR_LOCALIZATION = "ur_app_localization"
    }

    override fun invoke(): String = when (localeManager.currentLanguageCode) {
        LocaleCode.ENGLSIH.code -> {
            remoteConfigurationManager.getString(KEY_EN_LOCALIZATION)
        }

        LocaleCode.URDU.code -> {
            remoteConfigurationManager.getString(KEY_UR_LOCALIZATION)
        }

        else -> EMPTY_STRING
    }
}