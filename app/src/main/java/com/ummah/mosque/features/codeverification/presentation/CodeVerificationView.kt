package com.ummah.mosque.features.codeverification.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ummah.mosque.app.ui.theme.backgroundColor
import com.ummah.mosque.app.ui.theme.buttonColor
import com.ummah.mosque.common.composables.LoaderButtonView
import com.ummah.mosque.common.composables.OTP_VIEW_TYPE_BOX
import com.ummah.mosque.common.composables.OtpView
import com.ummah.mosque.common.utils.EMPTY_STRING

@Composable
fun CodeVerificationView(
    onVerificationCompleted: (String) -> Unit,
    viewModel: CodeVerificationViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val onCodeVerified by viewModel.onCodeVerificationSuccess.collectAsStateWithLifecycle()
    var otpText by remember {
        mutableStateOf(EMPTY_STRING)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Text(
            text = "Code Verification",
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
            text = "Please enter verification code received via sms.",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 16.dp
                )
        )

        OtpView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 42.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            charColor = Color.White,
            strokeColor = Color.White,
            otpCount = 6,
            type = OTP_VIEW_TYPE_BOX,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            onOtpTextChange = {
                otpText = it
            },
            containerSize = 50.dp,
            charSize = 24.sp,
            otpText = otpText
        )
        Spacer(modifier = Modifier.weight(1f))
        LoaderButtonView(
            onClick = {
                viewModel.onVerify(otpText)
            },
            text = "Verify",
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
        onCodeVerified?.let {
            onVerificationCompleted(it)
        }
    }

}

@Preview
@Composable
private fun CodeVerificationPreview() {
    CodeVerificationView(
        onVerificationCompleted = {}
    )
}