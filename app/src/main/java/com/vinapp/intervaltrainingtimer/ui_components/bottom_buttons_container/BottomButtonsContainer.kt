package com.vinapp.intervaltrainingtimer.ui_components.bottom_buttons_container

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

@Composable
fun BottomButtonsContainer(
    showLeftButton: Boolean = false,
    leftButtonText: String? = null,
    showCenterButton: Boolean = false,
    @DrawableRes centerButtonIcon: Int? = null,
    showRightButton: Boolean = false,
    rightButtonText: String? = null,
    onLeftButtonClick: () -> Unit = {},
    onCenterButtonClick: () -> Unit = {},
    onRightButtonClick: () -> Unit = {},
    centerButtonBaseColor: Color = AppTheme.colors.red,
    centerButtonIconColor: Color = AppTheme.colors.mediumGray,
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
            CenterButton(
                isDisplayed = showCenterButton,
                baseColor = centerButtonBaseColor,
                icon = centerButtonIcon,
                iconColor = centerButtonIconColor,
                onClick = onCenterButtonClick
            )
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
    isDisplayed: Boolean,
    baseColor: Color,
    @DrawableRes icon: Int?,
    iconColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1F),
        contentAlignment = Alignment.Center
    ) {
        if (isDisplayed) {
            CenterActionButton(
                baseColor = baseColor,
                icon = icon,
                iconColor = iconColor,
                onClick = onClick
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
            showLeftButton = true,
            leftButtonText = "Left",
            showCenterButton = true,
            centerButtonIcon = R.drawable.ic_play,
            showRightButton = true,
            rightButtonText = "Right"
        ) {
            Scaffold(
                bottomBar = {},
                backgroundColor = AppTheme.colors.lightGray
            ) {}
        }
    }
}