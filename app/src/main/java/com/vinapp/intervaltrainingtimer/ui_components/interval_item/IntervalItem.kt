package com.vinapp.intervaltrainingtimer.ui_components.interval_item

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

@Composable
fun IntervalItem(
    intervalItemData: IntervalItemData,
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(
                    top = 12.dp,
                ),
                text = intervalItemData.name,
                style = AppTheme.typography.title,
                color = AppTheme.colors.lightGrayFontColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(
                    vertical = 2.dp,
                ),
                text = intervalItemData.duration,
                style = AppTheme.typography.large,
                color = when (intervalItemData.color) {
                    IntervalColor.GREEN -> AppTheme.colors.green
                    IntervalColor.RED -> AppTheme.colors.red
                    IntervalColor.YELLOW -> AppTheme.colors.yellow
                    IntervalColor.WHITE -> AppTheme.colors.white
                }
            )
        }
    }
}

data class IntervalItemData(
    val id: Int,
    val name: String,
    val duration: String,
    val color: IntervalColor
)

@Preview(showBackground = true)
@Composable
private fun TimerItemPreview() {
    AppTheme {
        IntervalItem(
            intervalItemData = IntervalItemData(
                id = 0,
                name = "Interval name",
                duration = "15:00",
                color = IntervalColor.RED
            ),
            onClick = {}
        )
    }
}