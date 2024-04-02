package com.ovais.mosqueapp.common

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

interface LocaleManager {
    val currentLocate: Locale
    val currentLanguageCode: String
}

class DefaultLocaleManager @Inject constructor(
    @ApplicationContext private val context: Context
) : LocaleManager {
    private val _currentLocale by lazy {
        context.resources.configuration.locales[0]
    }
    override val currentLocate: Locale
        get() = _currentLocale
    override val currentLanguageCode: String
        get() = _currentLocale.language
}

enum class LocaleCode(val code: String) {
    ENGLSIH("en"),
    URDU("ur")
}