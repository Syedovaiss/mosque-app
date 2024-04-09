package com.ummah.mosque.common

import android.Manifest
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface PermissionManager {
    val isLocationAllowed: Boolean
}

class DefaultPermissionManager @Inject constructor(
    @ApplicationContext private val context: Context
) : PermissionManager {

    override val isLocationAllowed: Boolean
        get() = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PERMISSION_GRANTED

}