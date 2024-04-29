package com.ummah.mosque.core.presentation

import androidx.lifecycle.ViewModel
import com.ummah.mosque.R
import com.ummah.mosque.common.NetworkManager
import com.ummah.mosque.common.ToastManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MosqueActivityViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val toastManager: ToastManager
) : ViewModel() {

    val networkStatus: Flow<Boolean>
        get() = networkManager.isConnected

    fun notifyNetworkState(isConnected: Boolean) {
        if (isConnected.not()) {
            toastManager.show("Internet not connected!")
        }
    }
    fun getStatusBarColorRes(isConnected: Boolean): Int {
      return  if (isConnected) R.color.white else R.color.red
    }

}