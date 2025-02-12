package com.ovais.mosqueapp.features.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ovais.mosqueapp.R
import com.ovais.mosqueapp.app.ui.theme.backgroundColor
import com.ovais.mosqueapp.app.ui.theme.buttonColor
import com.ovais.mosqueapp.common.composables.ComposeLottieAnimation
import com.ovais.mosqueapp.common.composables.CustomTextField
import com.ovais.mosqueapp.common.composables.LoaderButtonView
import com.ovais.mosqueapp.common.utils.EMPTY_STRING

@Composable
fun LoginView(
    onLoggedIn: () -> Unit,
    onNewRegistration: () -> Unit,
    onForgotPassword: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val isLoginSuccess by viewModel.isLoginSuccess.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    var username by remember {
        mutableStateOf(EMPTY_STRING)
    }
    var password by remember {
        mutableStateOf(EMPTY_STRING)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        ComposeLottieAnimation(
            resId = R.raw.pray_anim_02,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 40.dp
                )
                .height(200.dp)

        )
        CustomTextField(
            enabled = true,
            label = "Username",
            labelStyle = TextStyle(
                fontWeight = FontWeight.SemiBold
            ),
            placeholderText = "Enter your username",
            placeholderColor = Color.Black.copy(alpha = 0.6f),
            placeholderStyle = TextStyle.Default,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
            onValueChanged = {
                username = it
            },
            defaultValue = username,
            labelColor = Color.White
        )
        CustomTextField(
            enabled = true,
            label = "Password",
            labelStyle = TextStyle(
                fontWeight = FontWeight.SemiBold
            ),
            placeholderText = "Enter your password",
            placeholderColor = Color.Black.copy(alpha = 0.6f),
            placeholderStyle = TextStyle.Default,
            imeAction = ImeAction.Done,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
            onValueChanged = {
                password = it
            },
            defaultValue = password,
            labelColor = Color.White,
            visualTransformation = PasswordVisualTransformation()
        )
        Text(
            text = "Forgot Password?",
            textAlign = TextAlign.Start,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 16.dp
                )
                .clickable { onForgotPassword() }
        )
        Spacer(Modifier.weight(1f))
        LoaderButtonView(
            onClick = {
                viewModel.onLogin(username, password)
            },
            text = "Sign In",
            modifier = {
                Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .height(48.dp)
            },
            fillColor = buttonColor,
            isLoading = isLoading
        )
        Text(
            text = "Don't have an account? Sign Up",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 24.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .clickable { onNewRegistration() },
            color = Color.White
        )
    }
    if (isLoginSuccess) {
        onLoggedIn()
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    widthDp = 360,
    heightDp = 720
)
@Composable
fun LoginPreview() {
    LoginView(
        onLoggedIn = {},
        onNewRegistration = {},
        onForgotPassword = {}
    )
}