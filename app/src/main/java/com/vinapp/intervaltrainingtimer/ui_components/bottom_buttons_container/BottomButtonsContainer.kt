package com.vinapp.intervaltrainingtimer.ui_components.bottom_buttons_container

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

@Composable
fun BottomButtonsContainer(
    showLeftButton: Boolean = true,
    leftButtonText: String? = null,
    showCenterButton: Boolean = true,
    showRightButton: Boolean = true,
    rightButtonText: String? = null,
    onLeftButtonClick: () -> Unit = {},
    onCenterButtonClick: () -> Unit = {},
    onRightButtonClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        content()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 18.dp
                )
                .height(64.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SideButton(
                isDisplayed = showLeftButton,
                text = leftButtonText,
                onClick = onLeftButtonClick,
                alignment = Alignment.CenterEnd
            )
            if (showCenterButton) {
                CenterButton(
                    onClick = onCenterButtonClick
                )
            }
            SideButton(
                isDisplayed = showRightButton,
                text = rightButtonText,
                onClick = onRightButtonClick,
                alignment = Alignment.CenterStart
            )
        }
    }
}

@Composable
private fun RowScope.CenterButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1F),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(AppTheme.colors.red)
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
                tint = AppTheme.colors.mediumGray
            )
        }
    }
}

@Composable
private fun RowScope.SideButton(
    isDisplayed: Boolean,
    text: String?,
    onClick: () -> Unit,
    alignment: Alignment,
) {
    Box(
        modifier = Modifier
            .weight(1F)
            .padding(horizontal = 16.dp),
        contentAlignment = alignment
    ) {
        if (isDisplayed && text != null) {
            Text(
                modifier = Modifier
                    .clickable(
                        onClick = onClick
                    ),
                text = text,
                style = AppTheme.typography.title.copy(
                    color = AppTheme.colors.whiteFontColor
                ),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
private fun BottomButtonsContainerPreview() {
    AppTheme {
        BottomButtonsContainer(
            leftButtonText = "Left",
            rightButtonText = "Right"
        ) {
            Scaffold(
                bottomBar = {

                },
                backgroundColor = AppTheme.colors.lightGray
            ) {}
        }
    }
}