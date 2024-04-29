package com.ummah.mosque.features.splash.presentation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ummah.mosque.R
import com.ummah.mosque.app.ui.theme.appGradient


@Composable
fun SplashView(
    onNextDestination: @Composable (String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val nextDestination by viewModel.nextDestination.collectAsStateWithLifecycle()
    val canRequestLocationPermissions by viewModel.canRequestLocationPermissions.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val openAppSettingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }
    val openAppSettings = {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", context.packageName, null)
        }
        openAppSettingsLauncher.launch(intent)
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.onLocationPermissionGranted()
        } else {
            openAppSettings()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = appGradient),
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.splash_mosque),
                contentDescription = null
            )
        }

    }

    nextDestination?.let { route ->
        onNextDestination(route)
    }
    if (canRequestLocationPermissions) {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

@Preview
@Composable
private fun SplashPreview() {
    SplashView(
        onNextDestination = {}
    )
}