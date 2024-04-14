package com.ummah.mosque.features.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ummah.mosque.R
import com.ummah.mosque.common.composables.RoundedButton


@Composable
fun MosqueDetailItem(
    modifier: Modifier = Modifier,
    onDeleteMosque: () -> Unit,
    onEditMosque: () -> Unit,
    onGetDirections: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Masjid e Mustafa",
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "Plot 001 Gulshan e Iqbal Block 4A, Karachi"
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            RoundedButton(iconRes = R.drawable.ic_directions) { onGetDirections() }
            RoundedButton(iconRes = R.drawable.ic_delete) { onDeleteMosque() }
            RoundedButton(iconRes = R.drawable.ic_edit) { onEditMosque() }
        }

    }
}