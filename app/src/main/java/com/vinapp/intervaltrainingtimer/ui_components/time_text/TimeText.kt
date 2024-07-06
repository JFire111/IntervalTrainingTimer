package com.vinapp.intervaltrainingtimer.ui_components.time_text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

@Composable
fun TimeText(
    modifier: Modifier = Modifier,
    timeDigits: TimeDigits,
    filledColor: Color = AppTheme.colors.red,
    unfilledColor: Color = AppTheme.colors.lightGrayFontColor,
    forceColored: Boolean = false,
) {
    Row(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
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
                },
                textAlign = TextAlign.Center,
                style = AppTheme.typography.large.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Bottom,
                        trim = LineHeightStyle.Trim.Both
                    ),
                )
            )
            Text(
                modifier = Modifier,
                text = stringResource(R.string.hintMinutes),
                style = AppTheme.typography.small.copy(
                    color = AppTheme.colors.grayFontColor,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Top,
                        trim = LineHeightStyle.Trim.Both
                    )
                )
            )
        }
        Text(
            text = buildAnnotatedString {
                coloredText(
                    text = ":",
                    isFilled = true,
                    filledColor = filledColor,
                    unfilledColor = unfilledColor,
                    forceColored = forceColored,
                )
            },
            textAlign = TextAlign.Center,
            style = AppTheme.typography.large.copy(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                ),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Bottom,
                    trim = LineHeightStyle.Trim.Both
                )
            )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    coloredDigit(
                        digit = timeDigits.third,
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
                style = AppTheme.typography.large.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Bottom,
                        trim = LineHeightStyle.Trim.Both
                    )
                )
            )
            Text(
                modifier = Modifier,
                text = stringResource(R.string.hintSeconds),
                style = AppTheme.typography.small.copy(
                    color = AppTheme.colors.grayFontColor,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Top,
                        trim = LineHeightStyle.Trim.Both
                    )
                )
            )
        }
    }
}

private fun AnnotatedString.Builder.coloredDigit(
    digit: Int?,
    filledColor: Color,
    unfilledColor: Color,
    forceColored: Boolean,
) {
    coloredText(
        text = "${digit ?: 0}",
        isFilled = digit != null,
        filledColor = filledColor,
        unfilledColor = unfilledColor,
        forceColored = forceColored
    )
}


private fun AnnotatedString.Builder.coloredText(
    text: String?,
    isFilled: Boolean,
    filledColor: Color,
    unfilledColor: Color,
    forceColored: Boolean,
) {
    withStyle(
        style = SpanStyle(
            color = if (isFilled || forceColored) {
                filledColor
            } else {
                unfilledColor
            },
        )
    ) {
        append(text)
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