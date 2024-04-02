package com.ovais.mosqueapp.features.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ovais.mosqueapp.R
import com.ovais.mosqueapp.app.ui.theme.backgroundColor


@Composable
fun SplashView(
    onNextDestination: (String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val nextDestination by viewModel.nextDestination.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = null,
        )
    }
    nextDestination?.let { route ->
        onNextDestination(route)
    }
}

@Preview
@Composable
private fun SplashPreview() {
    SplashView(
        onNextDestination = {}
    )
}