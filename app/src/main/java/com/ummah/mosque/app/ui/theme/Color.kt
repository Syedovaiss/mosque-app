package com.ummah.mosque.app.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val backgroundColor = Color(0xFF20242A)
val buttonColor = Color(0xFFf57e20)
private val gradientOne = Color(0xFF8F2480)
private val gradientTwo = Color(0XFFB54A80)
val iconPurple = Color(0xFF380C23)
val appGradient = Brush.linearGradient(
    colors = listOf(gradientOne, gradientTwo)
)
val darkishPurple: Color
    get() = gradientOne
val darkPurple: Color
    get() = gradientTwo