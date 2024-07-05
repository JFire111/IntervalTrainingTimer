package com.vinapp.intervaltrainingtimer.ui_components.timer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
fun Timer(
    modifier: Modifier = Modifier,
    radius: Dp? = null,
    width: Float = 32F,
    minPaddingBetweenTextAndRing: Dp = 16.dp,
    backgroundRingColor: Color = AppTheme.colors.mediumGray,
    foregroundRingColor: Color = AppTheme.colors.white,
    textStyle: TextStyle = AppTheme.typography.extraLarge.copy(
        color = AppTheme.colors.darkGrayFontColor
    ),
    timeConverter: TimeToStringConverter = DefaultTimeToStringConverter(),
    remainingTimerInMillisState: State<Long>,
    intervalProgressInPercentState: State<Float>
) {
    val remainingTimeStringState = remember {
        derivedStateOf {
            timeConverter.convert(remainingTimerInMillisState.value)
        }
    }
    val progressInDegreesState = remember {
        derivedStateOf {
            ((360F * intervalProgressInPercentState.value / 100F) * 2).roundToInt() / 2F
        }
    }
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(
        text = remainingTimeStringState.value,
        style = textStyle
    )

    Canvas(
        modifier = modifier.then(
            Modifier.fillMaxSize()
        ),
    ) {
        val ringRadius = radius?.toPx() ?: (size.minDimension / 2)
        val textScale = computeScale(
            textMaxDimension = textLayoutResult.size.toSize().maxDimension,
            ringRadius = ringRadius,
            padding = minPaddingBetweenTextAndRing.toPx()
        )
        timerRing(
            backgroundRingColor = backgroundRingColor,
            foregroundRingColor = foregroundRingColor,
            radius = ringRadius,
            ringWidth = width,
            swipeAngleState = progressInDegreesState,
        )
        scale(textScale) {
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = center.x - textLayoutResult.size.width / 2,
                    y = center.y - textLayoutResult.size.height / 2
                )
            )
        }
    }
}

private fun DrawScope.timerRing(
    backgroundRingColor: Color,
    foregroundRingColor: Color,
    radius: Float,
    ringWidth: Float,
    swipeAngleState: State<Float>,
) {
    drawCircle(
        color = backgroundRingColor,
        radius = radius - ringWidth / 2,
        style = Stroke(
            width = ringWidth
        )
    )
    drawArc(
        color = foregroundRingColor,
        startAngle = 0F,
        sweepAngle = swipeAngleState.value,
        useCenter = false,
        topLeft = Offset(
            x = center.x - radius + ringWidth / 2,
            y = center.y - radius + ringWidth / 2
        ),
        size = Size(
            width = radius * 2 - ringWidth,
            height = radius * 2 - ringWidth
        ),
        style = Stroke(
            width = ringWidth / 2.6F
        )
    )
}

private fun computeScale(textMaxDimension: Float, ringRadius: Float, padding: Float): Float {
    return if (((textMaxDimension + padding * 2) * sqrt(2F)) > (ringRadius * 2F)) {
        ringRadius * sqrt(2F) / (textMaxDimension + padding * 2)
    } else {
        1F
    }
}

@Preview
@Composable
private fun TimerPreview() {
    AppTheme {
        Timer(
            modifier = Modifier.background(AppTheme.colors.red),
            remainingTimerInMillisState = remember { mutableLongStateOf(900L) },
            intervalProgressInPercentState = remember { mutableFloatStateOf(50F) }
        )
    }
}