package com.ummah.mosque.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Provider

interface NetworkConnectivityProvider : Provider<Boolean>

class DefaultNetworkConnectivityProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkConnectivityProvider {

    override fun get(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetwork
        activeNetworkInfo?.let { network ->
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            return (
                    networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            ?: false || networkCapabilities?.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    ) ?: false
                    )
        }
        return false
    }
}