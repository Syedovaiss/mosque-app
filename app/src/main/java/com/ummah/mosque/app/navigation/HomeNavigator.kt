package com.ummah.mosque.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ummah.mosque.features.home.presentation.HomeView

object HomeNavigator {
    @Composable
    fun StartNavigation(
        navController: NavHostController,
        scaffoldPadding: PaddingValues = PaddingValues(0.dp),
        startDestination: String = HomeScreen.Home.routeId
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(route = HomeScreen.Home.routeId) {
                HomeView()
            }
        }
    }
}