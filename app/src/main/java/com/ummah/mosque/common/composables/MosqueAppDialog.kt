package com.ummah.mosque.common.composables

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ummah.mosque.R
import com.ummah.mosque.app.ui.theme.backgroundColor

@Composable
fun MosqueAppDialog(
    onDismissRequest: () -> Unit = {},
    title: String,
    description: String,
    negativeButtonText: String,
    positiveButtonText: String,
    isNonCancellable: Boolean = false,
    @RawRes topIcon: Int? = null,
    onPositiveButtonClick:() -> Unit,
    onNegativeButtonClick:() -> Unit

) {
    Dialog(
        onDismissRequest = {
            if (isNonCancellable.not()) {
                onDismissRequest()
            }
        }
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .padding(16.dp)
                .background(backgroundColor, shape = RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                topIcon?.let {
                    ComposeLottieAnimation(
                        resId = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 16.dp
                            )
                            .height(80.dp)
                    )
                }
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp
                        ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp
                        ),
                    textAlign = TextAlign.Center
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            bottom = 16.dp
                        )
                ) {
                    if (isNonCancellable.not()) {
                        Text(
                            text = negativeButtonText,
                            color = Color.White,
                            modifier = Modifier.clickable { onNegativeButtonClick() }
                        )
                    }
                    Text(
                        text = positiveButtonText,
                        color = Color.White,
                        modifier = Modifier.clickable { onPositiveButtonClick() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MosqueAppDialogPreview() {
    MosqueAppDialog(
        title = "Location Permission!",
        description = "Permission Needed",
        negativeButtonText = "Cancel",
        positiveButtonText = "Okay",
        isNonCancellable = true,
        topIcon = R.raw.alert_red,
        onPositiveButtonClick = {},
        onNegativeButtonClick = {}
    )
}