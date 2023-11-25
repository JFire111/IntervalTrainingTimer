package com.vinapp.intervaltrainingtimer.ui.timer_editor_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.domain.Interval
import com.vinapp.intervaltrainingtimer.ui_components.TimeDigits
import com.vinapp.intervaltrainingtimer.ui_components.TimeText
import com.vinapp.intervaltrainingtimer.ui_components.dialog.AppDialog
import com.vinapp.intervaltrainingtimer.ui_components.dialog.DialogButton
import com.vinapp.intervaltrainingtimer.ui_components.name_text_field.NameTextField
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme
import com.vinapp.intervaltrainingtimer.utils.TimeConverter

private const val MAX_INTERVAL_NAME_LENGTH = 128

@Composable
fun IntervalDialog(
    interval: Interval?,
    onConfirmClick: (name: String, duration: Long, color: IntervalColor) -> Unit,
    onCancelClick: () -> Unit,
) {
    var intervalName by remember(interval) {
        mutableStateOf(interval?.name ?: "")
    }
    var isIntervalNameError by remember(interval) {
        mutableStateOf(false)
    }
    var timeInputDigits by remember(interval) {
        mutableStateOf(
            if (interval != null) {
                TimeConverter.getTimeDigits(interval.duration)
            } else {
                TimeDigits()
            }
        )
    }
    var intervalColor by remember(interval) {
        mutableStateOf(interval?.color ?: IntervalColor.RED)
    }

    AppDialog(
        leftButton = DialogButton(
            text = stringResource(R.string.cancel),
            onClick = onCancelClick
        ),
        rightButton = DialogButton(
            text = stringResource(R.string.ok),
            onClick = {
                if (intervalName.isNullOrBlank()) {
                    isIntervalNameError = true
                } else {
                    onConfirmClick(
                        intervalName,
                        TimeConverter.getTimeLong(timeInputDigits),
                        intervalColor,
                    )
                }
            }
        ),
        onDismissRequest = onCancelClick
    ) {
        NameTextField(
            value = intervalName,
            isError = isIntervalNameError,
            placeholderText = stringResource(R.string.intervalName),
            onValueChange = {
                isIntervalNameError = false
                if (it.length < MAX_INTERVAL_NAME_LENGTH) {
                    intervalName = it
                }
            }
        )
        TimeRow(
            digits = timeInputDigits,
            onClearClick = {
                timeInputDigits = if (timeInputDigits.fourth != null) {
                    timeInputDigits.copy(fourth = null)
                } else if (timeInputDigits.third != null) {
                    timeInputDigits.copy(third = null)
                } else if (timeInputDigits.second != null) {
                    timeInputDigits.copy(second = null)
                } else if (timeInputDigits.first != null) {
                    timeInputDigits.copy(first = null)
                } else {
                    timeInputDigits
                }
            }
        )
        ColorSwitch(
            selectedColor = intervalColor,
            onColorClick = {
                intervalColor = it
            }
        )
        Keyboard(
            onButtonClick = { number ->
                timeInputDigits = if (timeInputDigits.first == null) {
                    timeInputDigits.copy(first = number)
                } else if (timeInputDigits.second == null) {
                    timeInputDigits.copy(second = number)
                } else if (timeInputDigits.third == null) {
                    timeInputDigits.copy(third = number)
                } else if (timeInputDigits.fourth == null) {
                    timeInputDigits.copy(fourth = number)
                } else {
                    timeInputDigits
                }
            }
        )
    }
}

@Composable
private fun TimeRow(
    digits: TimeDigits,
    onClearClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .size(32.dp)
        )
        TimeText(
            timeDigits = digits
        )
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable(
                    onClick = onClearClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(R.drawable.ic_delete_input),
                contentDescription = stringResource(R.string.clear),
                tint = AppTheme.colors.white
            )
        }
    }
}

@Composable
private fun ColorSwitch(
    selectedColor: IntervalColor,
    onColorClick: (IntervalColor) -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = Modifier
            .padding(
                horizontal = 32.dp,
                vertical = 10.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .clip(shape)
                .background(AppTheme.colors.lightGray)
                .clickable {
                    onColorClick(IntervalColor.GREEN)
                }
                .padding(
                    horizontal = 6.dp,
                    vertical = 4.dp
                ),
            text = stringResource(R.string.green),
            style = AppTheme.typography.regular.copy(
                color = if (selectedColor == IntervalColor.GREEN) {
                    AppTheme.colors.green
                } else {
                    AppTheme.colors.whiteFontColor
                }
            ),
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .clip(shape)
                .background(AppTheme.colors.lightGray)
                .clickable {
                    onColorClick(IntervalColor.GREEN)
                }
                .padding(
                    horizontal = 6.dp,
                    vertical = 4.dp
                ),
            text = stringResource(R.string.red),
            style = AppTheme.typography.regular.copy(
                color = if (selectedColor == IntervalColor.RED) {
                    AppTheme.colors.red
                } else {
                    AppTheme.colors.whiteFontColor
                }
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Keyboard(
    onButtonClick: (number: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                vertical = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        for (row in 0..2) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for(column in 0.. 2) {
                    val number = 1 + (row + column) + row * 2
                    KeyboardNumButton(
                        text = number.toString()
                    ) {
                        onButtonClick(number)
                    }
                }
            }
        }
        KeyboardNumButton(
            text = "0"
        ) {
            onButtonClick(0)
        }
    }
}

@Composable
private fun KeyboardNumButton(
    text: String,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(16.dp)
    Text(
        modifier = Modifier
            .clip(shape)
            .background(AppTheme.colors.lightGray)
            .clickable(
                onClick = onClick
            )
            .padding(
                horizontal = 28.dp,
                vertical = 8.dp
            ),
        text = text,
        style = AppTheme.typography.medium.copy(
            color = AppTheme.colors.whiteFontColor
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun IntervalDialogPreview() {
    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            IntervalDialog(
                interval = Interval(
                    id = 0,
                    timerId = "",
                    name = "Interval name",
                    duration = 900,
                    color = IntervalColor.RED
                ),
                onConfirmClick = { _, _, _ -> },
                onCancelClick = {},
            )
        }
    }
}