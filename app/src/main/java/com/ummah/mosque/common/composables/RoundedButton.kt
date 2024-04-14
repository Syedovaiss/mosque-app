package com.ummah.mosque.common.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ummah.mosque.R


@Composable
fun RoundedButton(
    width: Dp = 32.dp,
    height: Dp = 32.dp,
    @DrawableRes iconRes: Int,
    background: Color = Color.White.copy(0.8f),
    cornerSize: Dp = 10.dp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(cornerSize))
            .width(width)
            .height(height)
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun RoundedButtonPreview() {
    RoundedButton(
        iconRes = R.drawable.ic_edit,
        cornerSize = 8.dp
    ) {
        
    }
}