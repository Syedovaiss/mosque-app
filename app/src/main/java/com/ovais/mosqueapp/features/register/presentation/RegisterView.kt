package com.ovais.mosqueapp.features.register.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ovais.mosqueapp.R
import com.ovais.mosqueapp.app.ui.theme.backgroundColor
import com.ovais.mosqueapp.app.ui.theme.buttonColor
import com.ovais.mosqueapp.common.composables.ComposeLottieAnimation
import com.ovais.mosqueapp.common.composables.CustomDropDown
import com.ovais.mosqueapp.common.composables.CustomTextField
import com.ovais.mosqueapp.common.composables.LoaderButtonView
import com.ovais.mosqueapp.common.utils.EMPTY_STRING
import com.ovais.mosqueapp.common.utils.default


@Composable
fun RegisterView(
    onBackClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val countries by viewModel.countriesUiData.collectAsStateWithLifecycle()
    val cities by viewModel.citiesUiData.collectAsStateWithLifecycle()
    val isRegisteredSuccessfully by viewModel.isRegisteredSuccessfully.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
    ) {
        var username by remember {
            mutableStateOf(EMPTY_STRING)
        }
        var password by remember {
            mutableStateOf(EMPTY_STRING)
        }
        var phoneNumber by remember {
            mutableStateOf(EMPTY_STRING)
        }
        var city by remember {
            mutableStateOf(EMPTY_STRING)
        }
        var country by remember {
            mutableStateOf(EMPTY_STRING)
        }
        Image(
            painter = painterResource(
                id = R.drawable.ic_back_arrow
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp
                )
                .clickable { onBackClick() }
        )
        ComposeLottieAnimation(
            resId = R.raw.register_anim,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 32.dp
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
                    vertical = 16.dp
                ),
            onValueChanged = {
                phoneNumber = it
            },
            defaultValue = password,
            labelColor = Color.White,
            keyboardType = KeyboardType.Number
        )
        Text(
            text = "Country",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    bottom = 4.dp,
                    top = 8.dp
                ),
            textAlign = TextAlign.Start
        )
        CustomDropDown(
            items = countries,
            containerColor = Color.White,
            defaultText = countries.firstOrNull().default(),
            onValueSelected = {
                country = it
                viewModel.fetchCitiesByCountry(it)
            }
        )
        Text(
            text = "City",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    bottom = 4.dp,
                    top = 8.dp
                ),
            textAlign = TextAlign.Start
        )
        CustomDropDown(
            items = cities,
            containerColor = Color.White,
            defaultText = cities.firstOrNull().default(),
            onValueSelected = {
                city = it
            }
        )
        Spacer(Modifier.weight(1f))
        LoaderButtonView(
            onClick = {
                viewModel.onSignUp(username, password, phoneNumber, country, city)
            },
            text = "Sign Up",
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
        if (isRegisteredSuccessfully) {
            onSuccessfulRegistration()
        }
    }

}

@Preview
@Composable
private fun RegisterPreview() {
    RegisterView(
        onBackClick = {},
        onSuccessfulRegistration = {}
    )
}