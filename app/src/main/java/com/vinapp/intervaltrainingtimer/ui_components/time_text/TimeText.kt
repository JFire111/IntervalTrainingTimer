package com.vinapp.intervaltrainingtimer.ui_components.time_text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

@Composable
fun TimeText(
    modifier: Modifier = Modifier,
    timeDigits: TimeDigits,
    filledColor: Color = AppTheme.colors.red,
    unfilledColor: Color = AppTheme.colors.lightGrayFontColor,
    forceColored: Boolean = false,
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            coloredDigit(
                digit = timeDigits.first,
                filledColor = filledColor,
                unfilledColor = unfilledColor,
                forceColored = forceColored,
            )
            coloredDigit(
                digit = timeDigits.second,
                filledColor = filledColor,
                unfilledColor = unfilledColor,
                forceColored = forceColored,
            )
            coloredDigit(
                digit = timeDigits.third,
                prefix = ":",
                filledColor = filledColor,
                unfilledColor = unfilledColor,
                forceColored = forceColored,
            )
            coloredDigit(
                digit = timeDigits.fourth,
                filledColor = filledColor,
                unfilledColor = unfilledColor,
                forceColored = forceColored,
            )
        },
        textAlign = TextAlign.Center,
        style = AppTheme.typography.large
    )
}

private fun AnnotatedString.Builder.coloredDigit(
    digit: Int?,
    prefix: String? = null,
    filledColor: Color,
    unfilledColor: Color,
    forceColored: Boolean,
) {
    withStyle(
        style = SpanStyle(
            color = if (digit != null || forceColored) {
                filledColor
            } else {
                unfilledColor
            }
        )
    ) {
        prefix?.let { append(it) }
        append("${digit ?: 0}")
    }
}

@Preview
@Composable
private fun TimeTextPreview() {
    AppTheme {
        TimeText(
            timeDigits = TimeDigits(
                first = 1,
                second = 5,
                third = 0,
            )
        )
    }
}

data class TimeDigits(
    val first: Int? = null,
    val second: Int? = null,
    val third: Int? = null,
    val fourth: Int? = null,
)