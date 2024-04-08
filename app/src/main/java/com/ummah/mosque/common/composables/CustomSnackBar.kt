package com.ummah.mosque.common.composables

import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@Composable
fun CustomSnackBar(
    modifier: Modifier = Modifier,
    message: String
) {
    var snackBarVisible by remember { mutableStateOf(false) }
    if (snackBarVisible) {
        Snackbar(
            modifier = modifier,
            action = {
                Button(onClick = {
                    snackBarVisible = false
                }) {
                    Text("Dismiss")
                }
            }
        ) {
            Text(message)
        }
    }
}