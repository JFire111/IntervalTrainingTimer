package com.vinapp.intervaltrainingtimer.ui_components.timer_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

@Composable
fun TimerItem(
    timerItemData: TimerItemData,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    Card(
        modifier = Modifier
            .height(110.dp),
        backgroundColor = AppTheme.colors.darkGray,
        shape = shape,
    ) {
        Column(
            modifier = Modifier
                .clickable(
                    onClick = onClick
                )
                .fillMaxWidth()
                .padding(
                    horizontal = 36.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(
                    top = 12.dp,
                ),
                text = timerItemData.name,
                style = AppTheme.typography.title,
                color = AppTheme.colors.lightGrayFontColor,
            )
            Text(
                modifier = Modifier.padding(
                    vertical = 2.dp,
                ),
                text = timerItemData.duration,
                style = AppTheme.typography.large,
                color = AppTheme.colors.red
            )
        }
    }
}

data class TimerItemData(
    val id: String,
    val name: String,
    val duration: String,
)

@Preview(showBackground = true)
@Composable
private fun TimerItemPreview() {
    AppTheme {
        TimerItem(
            timerItemData = TimerItemData(
                id = "",
                name = "Timer name",
                duration = "15:00"
            ),
            onClick = {}
        )
    }
}