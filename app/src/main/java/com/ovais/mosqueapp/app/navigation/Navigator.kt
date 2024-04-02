package com.ovais.mosqueapp.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ovais.mosqueapp.common.utils.EMPTY_STRING
import com.ovais.mosqueapp.features.codeverification.presentation.CodeVerificationView
import com.ovais.mosqueapp.features.forgotpassword.presentation.ForgotPasswordView
import com.ovais.mosqueapp.features.home.presentation.HomeView
import com.ovais.mosqueapp.features.login.presentation.LoginView
import com.ovais.mosqueapp.features.passwordreset.presentation.PasswordResetView
import com.ovais.mosqueapp.features.register.presentation.RegisterView
import com.ovais.mosqueapp.features.splash.presentation.SplashView


const val ARGS_DATA = "args_data"

object MosqueNavigator {

    @Composable
    fun StartNavigation(
        navController: NavHostController,
        scaffoldPadding: PaddingValues = PaddingValues(0.dp),
        startDestination: String = Screens.Splash.routeId
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(route = Screens.Splash.routeId) {
                SplashView(
                    onNextDestination = { nextDestination ->
                        navController.navigate(nextDestination)
                    }
                )
            }
            composable(route = Screens.Login.routeId) {
                LoginView(
                    onLoggedIn = {
                        navController.navigate(Screens.Home.routeId)
                    },
                    onNewRegistration = {
                        navController.navigate(Screens.Register.routeId)
                    },
                    onForgotPassword = {
                        navController.navigate(Screens.ForgotPassword.routeId)
                    }
                )
            }
            composable(route = Screens.Register.routeId) {
                RegisterView(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSuccessfulRegistration = {
                        navController.navigate(Screens.Login.routeId)
                    }
                )
            }
            composable(route = Screens.ForgotPassword.routeId) {
                ForgotPasswordView(
                    onCodeSent = {
                        navController.navigate("${Screens.CodeVerification.routeId}/$it")
                    }
                )
            }
            composable(
                route = "${Screens.CodeVerification.routeId}/{$ARGS_DATA}",
                arguments = listOf(navArgument(name = ARGS_DATA) { defaultValue = EMPTY_STRING })
            ) {
                CodeVerificationView(
                    onVerificationCompleted = { phoneNumber ->
                        navController.navigate("${Screens.PasswordReset.routeId}/$phoneNumber")
                    }
                )
            }
            composable(
                route = "${Screens.PasswordReset.routeId}/{$ARGS_DATA}",
                arguments = listOf(navArgument(name = ARGS_DATA) { defaultValue = EMPTY_STRING })
            ) {
                PasswordResetView(
                    onPasswordResetSuccess = {
                        navController.navigate(Screens.Login.routeId)
                    }
                )
            }
            composable(route = Screens.Home.routeId) {
                HomeView()
            }
        }
    }
}