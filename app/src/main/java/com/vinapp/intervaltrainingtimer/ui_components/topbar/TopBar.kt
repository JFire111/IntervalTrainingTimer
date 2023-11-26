package com.vinapp.intervaltrainingtimer.ui_components.topbar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    leftButtonIcon: Painter? = null,
    onLeftButtonClick: () -> Unit = {},
    rightButtonIcon: Painter? = null,
    onRightButtonClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(AppTheme.colors.darkGray)
            .height(64.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TopBarButton(
            painter = leftButtonIcon,
            onClick = onLeftButtonClick
        )
        Box(
            modifier = Modifier
                .weight(1F)
        ) {
            content()
        }
        TopBarButton(
            painter = rightButtonIcon,
            onClick = onRightButtonClick
        )
    }
}

@Composable
private fun TopBarButton(
    painter: Painter?,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        painter?.let {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        onClick = onClick
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = it,
                    contentDescription = null
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
private fun TopBarPreview() {
    AppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    content = {
                        Box(
                            modifier = Modifier
                                .background(AppTheme.colors.yellow)
                                .fillMaxSize()
                        )
                    }
                )
            },
            backgroundColor = AppTheme.colors.lightGray
        ) {}
    }
}