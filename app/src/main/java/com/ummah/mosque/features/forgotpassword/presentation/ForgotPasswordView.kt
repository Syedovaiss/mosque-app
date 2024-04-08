package com.ummah.mosque.features.forgotpassword.presentation

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.ummah.mosque.common.utils.getActivity

@Composable
fun ForgotPasswordView(
    onCodeSent: (String?) -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val activity = LocalContext.current.getActivity()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val canNavigateForVerification by viewModel.canNavigateForVerification.collectAsStateWithLifecycle()
    var phoneNumber by remember {
        mutableStateOf(EMPTY_STRING)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Text(
            text = "Forgot Password",
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
            text = "Please enter your number to verify your identity",
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
            label = "Phone Number",
            labelStyle = TextStyle(
                fontWeight = FontWeight.SemiBold
            ),
            placeholderText = "Enter your phone number",
            placeholderColor = Color.Black.copy(alpha = 0.6f),
            placeholderStyle = TextStyle.Default,
            imeAction = ImeAction.Done,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 48.dp
                ),
            onValueChanged = {
                phoneNumber = it
            },
            defaultValue = phoneNumber,
            labelColor = Color.White,
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.weight(1f))
        LoaderButtonView(
            onClick = {
                viewModel.sendVerificationCode(phoneNumber,activity )
            },
            text = "Send Verification Code",
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
        canNavigateForVerification?.let {
            onCodeSent(it)
        }
    }
}

@Preview
@Composable
private fun ForgotPasswordPreview() {
    ForgotPasswordView(
        onCodeSent = {

        }
    )
}