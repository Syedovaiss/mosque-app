package com.ummah.mosque.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ummah.mosque.common.utils.EMPTY_STRING
import com.ummah.mosque.features.codeverification.presentation.CodeVerificationView
import com.ummah.mosque.features.forgotpassword.presentation.ForgotPasswordView
import com.ummah.mosque.features.login.presentation.LoginView
import com.ummah.mosque.features.passwordreset.presentation.PasswordResetView
import com.ummah.mosque.features.register.presentation.RegisterView
import com.ummah.mosque.features.splash.presentation.SplashView


const val ARGS_DATA = "args_data"

object AuthNavigator {

    @Composable
    fun StartNavigation(
        navController: NavHostController,
        scaffoldPadding: PaddingValues = PaddingValues(0.dp),
        startDestination: String = AuthScreen.Splash.routeId
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(route = AuthScreen.Splash.routeId) {
                SplashView(
                    onNextDestination = { nextDestination ->
                        if (nextDestination == HomeScreen.Home.routeId) {
                            HomeNavigator.StartNavigation(navController = rememberNavController())
                        } else {
                            navController.navigate(nextDestination)
                        }
                    }
                )
            }
            composable(route = AuthScreen.Login.routeId) {
                LoginView(
                    onLoggedIn = {
                        HomeNavigator.StartNavigation(navController = rememberNavController())
                    },
                    onNewRegistration = {
                        navController.navigate(AuthScreen.Register.routeId)
                    },
                    onForgotPassword = {
                        navController.navigate(AuthScreen.ForgotPassword.routeId)
                    }
                )
            }
            composable(route = AuthScreen.Register.routeId) {
                RegisterView(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSuccessfulRegistration = {
                        navController.navigate(AuthScreen.Login.routeId)
                    }
                )
            }
            composable(route = AuthScreen.ForgotPassword.routeId) {
                ForgotPasswordView(
                    onCodeSent = {
                        navController.navigate("${AuthScreen.CodeVerification.routeId}/$it")
                    }
                )
            }
            composable(
                route = "${AuthScreen.CodeVerification.routeId}/{$ARGS_DATA}",
                arguments = listOf(navArgument(name = ARGS_DATA) { defaultValue = EMPTY_STRING })
            ) {
                CodeVerificationView(
                    onVerificationCompleted = { phoneNumber ->
                        navController.navigate("${AuthScreen.PasswordReset.routeId}/$phoneNumber")
                    }
                )
            }
            composable(
                route = "${AuthScreen.PasswordReset.routeId}/{$ARGS_DATA}",
                arguments = listOf(navArgument(name = ARGS_DATA) { defaultValue = EMPTY_STRING })
            ) {
                PasswordResetView(
                    onPasswordResetSuccess = {
                        navController.navigate(AuthScreen.Login.routeId)
                    }
                )
            }
        }
    }
}