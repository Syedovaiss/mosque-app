package com.ummah.mosque.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject


interface FirebaseLoggingService {
    fun logInfo(vararg message: String)
    fun logException(vararg message: String)
}

class DefaultFirebaseLoggingService @Inject constructor() : FirebaseLoggingService {

    private val crashlyticsInstance by lazy { FirebaseCrashlytics.getInstance() }

    override fun logInfo(vararg message: String) {
        crashlyticsInstance.log(message.contentDeepToString())
    }

    override fun logException(vararg message: String) {
        crashlyticsInstance.recordException(
            Throwable(
                message.contentDeepToString(),
                null
            )
        )
    }
}