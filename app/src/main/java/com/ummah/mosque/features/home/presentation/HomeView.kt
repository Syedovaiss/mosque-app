package com.ummah.mosque.features.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ummah.mosque.R
import com.ummah.mosque.app.ui.theme.backgroundColor
import com.ummah.mosque.common.composables.AutoScrollingCarousel
import com.ummah.mosque.common.composables.ScreenLoader


@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel(),
    onCreateMosque: () -> Unit,
    onProfileClick: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val carouselItems by viewModel.carouselItems.collectAsStateWithLifecycle()
    val availableMosques by viewModel.mosqueItems.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(scrollState)
    ) {
        if (isLoading) {
            ScreenLoader()
        } else {
            if (carouselItems.isNotEmpty()) {
                AutoScrollingCarousel(
                    items = carouselItems,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 16.dp
                        )
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 32.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                RoundedCardView(
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp),
                    title = "Create Mosque",
                    icon = R.drawable.create_mosque
                )

                RoundedCardView(
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp),
                    title = "Profile",
                    icon = R.drawable.profile
                )
            }
            if (availableMosques.isNotEmpty()) {
                availableMosques.forEach {
                    MosqueDetailItem(
                        onDeleteMosque = { },
                        onEditMosque = {

                        },
                        onGetDirections = {

                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    HomeView(
        onCreateMosque = {},
        onProfileClick = {}
    )
}