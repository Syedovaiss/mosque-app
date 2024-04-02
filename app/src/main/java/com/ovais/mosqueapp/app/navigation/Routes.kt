package com.ovais.mosqueapp.app.navigation


private const val SCREEN_PREFIX = "screen_"

interface ScreenRoutes {
    val routeId: String
}

sealed class Screens(
    override val routeId: String,
    val title: String
) : ScreenRoutes {

    data object Splash : Screens(
        routeId = buildRoute("splash"),
        title = "Splash"
    )

    data object Login : Screens(
        routeId = buildRoute("sign_in"),
        title = "Login"
    )

    data object Register : Screens(
        routeId = buildRoute("sign_up"),
        title = "register"
    )

    data object ForgotPassword : Screens(
        routeId = buildRoute("forgot_password"),
        title = "Forgot Password"
    )

    data object CodeVerification : Screens(
        routeId = buildRoute("code_verification"),
        title = "Code Verification"
    )
    data object PasswordReset : Screens(
        routeId = buildRoute("password_reset"),
        title = "Password Reset"
    )

    data object Home : Screens(
        routeId = buildRoute("home"),
        title = "Home"
    )

    fun withArgs(vararg args: String): String {
        return buildString {
            append(routeId)
            append("/$args")
        }
    }
    fun withArgs(route: String,vararg args: String): String {
        return buildString {
            append(route)
            append("/$args")
        }
    }
}

private fun buildRoute(id: String) = buildString {
    append(SCREEN_PREFIX)
    append(id)
}