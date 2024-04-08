package com.ummah.mosque.app.navigation


private const val SCREEN_PREFIX = "screen_"

interface AuthRoutes {
    val routeId: String
}

sealed class AuthScreen(
    override val routeId: String,
    val title: String
) : AuthRoutes {

    data object Splash : AuthScreen(
        routeId = buildRoute("splash"),
        title = "Splash"
    )

    data object Login : AuthScreen(
        routeId = buildRoute("sign_in"),
        title = "Login"
    )

    data object Register : AuthScreen(
        routeId = buildRoute("sign_up"),
        title = "register"
    )

    data object ForgotPassword : AuthScreen(
        routeId = buildRoute("forgot_password"),
        title = "Forgot Password"
    )

    data object CodeVerification : AuthScreen(
        routeId = buildRoute("code_verification"),
        title = "Code Verification"
    )
    data object PasswordReset : AuthScreen(
        routeId = buildRoute("password_reset"),
        title = "Password Reset"
    )
}

private fun buildRoute(id: String) = buildString {
    append(SCREEN_PREFIX)
    append(id)
}