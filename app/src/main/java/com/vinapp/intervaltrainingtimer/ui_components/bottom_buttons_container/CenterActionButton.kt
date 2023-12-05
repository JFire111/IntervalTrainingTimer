package com.vinapp.intervaltrainingtimer.ui_components.bottom_buttons_container

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

@Composable
fun CenterActionButton(
    baseColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(baseColor)
            .size(64.dp)
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(R.drawable.ic_play),
            contentDescription = null,
            tint = iconColor
        )
    }
}

@Preview
@Composable
private fun CenterActionButtonPreview() {
    AppTheme {
        CenterActionButton(
            baseColor = AppTheme.colors.red,
            iconColor = AppTheme.colors.mediumGray,
        ) {}
    }
}