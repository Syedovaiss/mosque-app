package com.ummah.mosque.features.passwordreset.presentation

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ummah.mosque.app.ui.theme.backgroundColor
import com.ummah.mosque.app.ui.theme.buttonColor
import com.ummah.mosque.common.composables.CustomTextField
import com.ummah.mosque.common.composables.LoaderButtonView
import com.ummah.mosque.common.utils.EMPTY_STRING

@Composable
fun PasswordResetView(
    onPasswordResetSuccess: () -> Unit,
    viewModel: PasswordResetViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isPasswordResetSuccess by viewModel.isPasswordResetSuccess.collectAsStateWithLifecycle()
    var password by remember {
        mutableStateOf(EMPTY_STRING)
    }

    var reEnteredPassword by remember {
        mutableStateOf(EMPTY_STRING)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        Text(
            text = "Reset Password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 48.dp,
                    start = 16.dp
                ),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White
        )
        Text(
            text = "Please enter your new password.",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 16.dp
                )
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

        CustomTextField(
            enabled = true,
            label = "Retype Password",
            labelStyle = TextStyle(
                fontWeight = FontWeight.SemiBold
            ),
            placeholderText = "Re-enter your password",
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
                reEnteredPassword = it
            },
            defaultValue = reEnteredPassword,
            labelColor = Color.White,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.weight(1f))
        LoaderButtonView(
            onClick = {
                viewModel.resetPassword(password, reEnteredPassword)
            },
            text = "Update",
            modifier = {
                Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp
                    )
                    .height(48.dp)
            },
            fillColor = buttonColor,
            isLoading = isLoading
        )
        if (isPasswordResetSuccess) {
            onPasswordResetSuccess()
        }
    }
}

@Preview
@Composable
private fun PasswordResetPreview() {
    PasswordResetView(
        onPasswordResetSuccess = {}
    )
}