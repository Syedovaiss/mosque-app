package com.ummah.mosque.common

import androidx.viewbinding.BuildConfig
import com.ummah.mosque.common.utils.default
import com.ummah.mosque.firebase.FirebaseLoggingService
import timber.log.Timber
import javax.inject.Inject

interface AppLogger {
    fun debug(message: String?)
    fun debug(tag: String?, message: String?)

    fun warn(message: String?)
    fun warn(tag: String?, message: String?)

    fun info(message: String?, logOnFirebase: Boolean = false)
    fun info(tag: String?, message: String?, logOnFirebase: Boolean = false)

    fun error(message: String?, logOnFirebase: Boolean = false)
    fun error(tag: String?, message: String?, logOnFirebase: Boolean = false)
    fun logOnFirebase(message: String?)
    fun recordOnFirebase(message: String?)
}
class DefaultAppLogger @Inject constructor(
    private val firebaseLoggingService: FirebaseLoggingService
):AppLogger {

    override fun debug(message: String?) {
        if (BuildConfig.DEBUG) {
            Timber.d(message)
        }
    }

    override fun debug(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) {
            Timber.d("$tag:$message")
        }
    }

    override fun warn(message: String?) {
        if (BuildConfig.DEBUG) {
            Timber.w(message)
        }
    }

    override fun warn(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) {
            Timber.w("$tag:$message")
        }
    }

    override fun info(message: String?, logOnFirebase: Boolean) {
        if (BuildConfig.DEBUG) {
            if (logOnFirebase) {
                firebaseLoggingService.logInfo(message.default())
            }
            Timber.i(message)
        }
    }

    override fun info(tag: String?, message: String?, logOnFirebase: Boolean) {
        if (BuildConfig.DEBUG) {
            if (logOnFirebase) {
                firebaseLoggingService.logInfo(message.default())
            }
            Timber.i("$tag:$message")
        }
    }

    override fun error(message: String?, logOnFirebase: Boolean) {
        if (BuildConfig.DEBUG) {
            if (logOnFirebase) {
                firebaseLoggingService.logException(message.default())
            }
            Timber.e(message)
        }
    }

    override fun error(tag: String?, message: String?, logOnFirebase: Boolean) {
        if (BuildConfig.DEBUG) {
            if (logOnFirebase) {
                firebaseLoggingService.logException(message.default())
            }
            Timber.e("$tag:$message")
        }
    }

    override fun logOnFirebase(message: String?) {
        firebaseLoggingService.logInfo(message.default())
    }

    override fun recordOnFirebase(message: String?) {
        firebaseLoggingService.logException(message.default())
    }
}