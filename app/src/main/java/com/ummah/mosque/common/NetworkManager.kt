package com.ummah.mosque.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.ummah.mosque.common.composables.isVersionGreaterThanEquals
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface NetworkManager {
    val isConnected: Flow<Boolean>
    val isWifiConnection: Boolean
}

class DefaultNetworkManager @Inject constructor(
    private val networkStatusProvider: NetworkConnectivityProvider,
    private val loggingService: AppLogger,
    @ApplicationContext private val context: Context
) : NetworkManager {

    private val connectivityManager by lazy {
        context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    }

    @SuppressLint("MissingPermission")
    override val isConnected: Flow<Boolean> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                loggingService.debug("connected")
                trySend(true)
            }

            override fun onLost(network: Network) {
                trySend(false)
                loggingService.debug("disconnected")
                super.onLost(network)
            }
        }
        val request =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .apply {
                    if (isVersionGreaterThanEquals(Build.VERSION_CODES.M)) {
                        addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    }
                }.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()
        trySend(networkStatusProvider.get())
        connectivityManager.registerNetworkCallback(request, callback)
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

    override val isWifiConnection: Boolean
        get() = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
}