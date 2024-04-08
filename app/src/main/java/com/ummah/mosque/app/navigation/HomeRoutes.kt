package com.ummah.mosque.app.navigation

private const val SCREEN_PREFIX = "screen_"

interface HomeRoutes {
    val routeId: String
}

sealed class HomeScreen(
    override val routeId: String,
    val title: String
) : HomeRoutes {

    data object Home : HomeScreen(
        routeId = buildRoute("home"),
        title = "Home"
    )
}

private fun buildRoute(id: String) = buildString {
    append(SCREEN_PREFIX)
    append(id)
}