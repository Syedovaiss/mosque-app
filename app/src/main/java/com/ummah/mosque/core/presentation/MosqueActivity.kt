package com.ummah.mosque.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.ummah.mosque.app.navigation.AuthNavigator
import com.ummah.mosque.app.ui.theme.MosqueAppTheme
import com.ummah.mosque.common.composables.collectFlowOnLifecycle
import com.ummah.mosque.common.composables.getColorRes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MosqueActivity : ComponentActivity() {
    private val viewModel: MosqueActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MosqueAppTheme {
                ListenToNetworkUpdates()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthNavigator.StartNavigation(
                        navController = navController
                    )
                }
            }
        }
    }

    @Composable
    private fun ListenToNetworkUpdates() {
        val context = LocalContext.current as ComponentActivity
        DisposableEffect(true) {
            collectFlowOnLifecycle(
                flow = viewModel.networkStatus,
                block = { isConnected ->
                    viewModel.notifyNetworkState(isConnected)
                    val statusBarColor = getColorRes(
                        resId = viewModel.getStatusBarColorRes(isConnected)
                    )
                    context.enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.light(
                            statusBarColor, statusBarColor
                        )
                    )
                }
            )
            onDispose { }
        }
    }
}