package com.vinapp.intervaltrainingtimer.presentation.timer_editor_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.presentation.timer_editor_screen.TimerEditorScreenAction.NavigateToTimerScreen
import com.vinapp.intervaltrainingtimer.presentation.timer_editor_screen.TimerEditorScreenAction.NavigateBack
import com.vinapp.intervaltrainingtimer.presentation.timer_editor_screen.TimerEditorScreenAction.ShowDurationErrorSnackbar
import com.vinapp.intervaltrainingtimer.ui_components.time_text.TimeDigits
import com.vinapp.intervaltrainingtimer.ui_components.time_text.TimeText
import com.vinapp.intervaltrainingtimer.ui_components.topbar.TopBar
import com.vinapp.intervaltrainingtimer.ui_components.add_item.AddItem
import com.vinapp.intervaltrainingtimer.ui_components.bottom_buttons_container.BottomButtonsContainer
import com.vinapp.intervaltrainingtimer.ui_components.interval_item.IntervalItem
import com.vinapp.intervaltrainingtimer.ui_components.interval_item.IntervalItemData
import com.vinapp.intervaltrainingtimer.ui_components.name_text_field.NameTextField
import com.vinapp.intervaltrainingtimer.ui_components.count_picker.TimePicker
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun TimerEditorScreen(
    timerId: String?,
    navigateToTimerScreen: (timerId: String) -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel: TimerEditorScreenViewModel = viewModel(
        factory = TimerEditorScreenViewModel.Factory
    )
    val state by viewModel.screenStateFlow.collectAsState()
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()
    val snackbarMessage = stringResource(id = R.string.totalTimeError)

    LaunchedEffect(Unit) {
        launch {
            viewModel.screenActionFlow.collect { action ->
                when (action) {
                    is NavigateToTimerScreen -> navigateToTimerScreen(action.timerId)
                    NavigateBack -> navigateBack()
                    ShowDurationErrorSnackbar -> scaffoldState.snackbarHostState.showSnackbar(
                        message = snackbarMessage
                    )
                }
            }
        }
        viewModel.loadTimerById(timerId)
    }

    TimerEditorScreenContent(
        state = state,
        scaffoldState = scaffoldState,
        onTimerNameChanged = viewModel::onTimerNameChanged,
        onIncreaseRoundsClick = {
            focusManager.clearFocus()
            viewModel.onIncreaseRoundsClick()
        },
        onDecreaseRoundsClick = {
            focusManager.clearFocus()
            viewModel.onDecreaseRoundsClick()
        },
        onIncreaseStartDelayClick = {
            focusManager.clearFocus()
            viewModel.onIncreaseStartDelayClick()
        },
        onDecreaseStartDelayClick = {
            focusManager.clearFocus()
            viewModel.onDecreaseStartDelayClick()
        },
        onIncreaseTimeBetweenRoundsClick = {
            focusManager.clearFocus()
            viewModel.onIncreaseTimeBetweenRoundsClick()
        },
        onDecreaseTimeBetweenRoundsClick = {
            focusManager.clearFocus()
            viewModel.onDecreaseTimeBetweenRoundsClick()
        },
        onIntervalClick = {
            focusManager.clearFocus()
            viewModel.onIntervalClick(it)
        },
        onAddIntervalClick = {
            focusManager.clearFocus()
            viewModel.onAddIntervalClick()
        },
        onIntervalDialogConfirmClick = { name, duration, color ->
            focusManager.clearFocus()
            viewModel.onSaveIntervalClick(name, duration, color)
        },
        onIntervalDialogCancelClick = {
            focusManager.clearFocus()
            viewModel.closeDialog()
        },
        onDeleteClick = {
            focusManager.clearFocus()
            viewModel.onDeleteTimerClick()
        },
        onStartClick = {
            focusManager.clearFocus()
            viewModel.onStartTimerClick()
        },
        onSaveClick = {
            focusManager.clearFocus()
            viewModel.onSaveTimerClick()
        },
    )
}

@Composable
private fun TimerEditorScreenContent(
    state: TimerEditorScreenState,
    scaffoldState: ScaffoldState,
    onTimerNameChanged: (name: String) -> Unit,
    onIncreaseRoundsClick: () -> Unit,
    onDecreaseRoundsClick: () -> Unit,
    onIncreaseStartDelayClick: () -> Unit,
    onDecreaseStartDelayClick: () -> Unit,
    onIncreaseTimeBetweenRoundsClick: () -> Unit,
    onDecreaseTimeBetweenRoundsClick: () -> Unit,
    onIntervalClick: (index: Int) -> Unit,
    onAddIntervalClick: () -> Unit,
    onIntervalDialogConfirmClick: (name: String, duration: Long, color: IntervalColor) -> Unit,
    onIntervalDialogCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onStartClick: () -> Unit,
    onSaveClick: () -> Unit,
) {

    BottomButtonsContainer(
        showRightButton = true,
        leftButtonText = stringResource(R.string.delete),
        rightButtonText = stringResource(R.string.save),
        showLeftButton = state.showDeleteButton,
        showCenterButton = true,
        centerButtonIcon = R.drawable.ic_play,
        onLeftButtonClick = onDeleteClick,
        onCenterButtonClick = onStartClick,
        onRightButtonClick = onSaveClick
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = AppTheme.colors.darkGray,
            topBar = {
                TopBar(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                        ),
                    content = {
                        NameTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = state.timerName ?: "",
                            isError = state.isTimerNameError,
                            placeholderText = stringResource(R.string.timerName),
                            onValueChange = onTimerNameChanged
                        )
                    },
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 14.dp,
                            top = 10.dp,
                            end = 10.dp,
                            bottom = 8.dp,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TotalTimeRow(
                        timeDigits = state.totalTimeDigits,
                        hasError = state.isDurationError
                    )
                    NumberOfRoundsRow(
                        numberOfRounds = state.numberOfRounds,
                        onIncreaseClick = onIncreaseRoundsClick,
                        onDecreaseClick = onDecreaseRoundsClick
                    )
                    StartDelayRow(
                        startDelay = state.startDelay,
                        onIncreaseClick = onIncreaseStartDelayClick,
                        onDecreaseClick = onDecreaseStartDelayClick
                    )
                    TimeBetweenRoundsRow(
                        timeBetweenRounds = state.timeBetweenRounds,
                        onIncreaseClick = onIncreaseTimeBetweenRoundsClick,
                        onDecreaseClick = onDecreaseTimeBetweenRoundsClick
                    )
                }
                Divider(
                    color = AppTheme.colors.mediumGray
                )
                Text(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp
                        ),
                    text = stringResource(R.string.intervalList),
                    style = AppTheme.typography.regular.copy(
                        color = AppTheme.colors.grayFontColor
                    )
                )
                Divider(
                    color = AppTheme.colors.mediumGray
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(AppTheme.colors.lightGray),
                    contentPadding = PaddingValues(
                        start = 14.dp,
                        top = 8.dp,
                        end = 14.dp,
                        bottom = 128.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.intervalList?.let { intervals ->
                        itemsIndexed(intervals) { index, interval ->
                            IntervalItem(
                                intervalItemData = interval,
                                onClick = {
                                    onIntervalClick(index)
                                }
                            )
                        }
                    }
                    item {
                        AddItem(
                            onClick = onAddIntervalClick
                        )
                    }
                }
            }
            if (state.showIntervalDialog) {
                IntervalDialog(
                    interval = state.selectedInterval,
                    onConfirmClick = onIntervalDialogConfirmClick,
                    onCancelClick = onIntervalDialogCancelClick
                )
            }
        }
    }
}

@Composable
private fun SettingsRow(
    title: String,
    hasError: Boolean = false,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1F),
            text = "$title:",
            textAlign = TextAlign.End,
            style = AppTheme.typography.title.copy(
                color = if (hasError) {
                    AppTheme.colors.red
                } else {
                    AppTheme.colors.grayFontColor
                }
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8F),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
private fun TotalTimeRow(
    timeDigits: TimeDigits,
    hasError: Boolean
) {
    SettingsRow(
        title = stringResource(R.string.totalTimeText),
        hasError = hasError
    ) {
        TimeText(
            timeDigits = timeDigits,
            forceColored = true
        )
    }
}

@Composable
private fun NumberOfRoundsRow(
    numberOfRounds: Int,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
) {
    SettingsRow(
        title = stringResource(R.string.numberOfCyclesText)
    ) {
        TimePicker(
            value = numberOfRounds,
            onIncreaseClick = onIncreaseClick,
            onDecreaseClick = onDecreaseClick
        )
    }
}

@Composable
private fun StartDelayRow(
    startDelay: Int,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
) {
    SettingsRow(
        title = stringResource(R.string.delayBeforeStart)
    ) {
        TimePicker(
            value = startDelay,
            onIncreaseClick = onIncreaseClick,
            onDecreaseClick = onDecreaseClick
        )
    }
}

@Composable
private fun TimeBetweenRoundsRow(
    timeBetweenRounds: Int,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
) {
    SettingsRow(
        title = stringResource(R.string.timeBetweenRounds)
    ) {
        TimePicker(
            value = timeBetweenRounds,
            onIncreaseClick = onIncreaseClick,
            onDecreaseClick = onDecreaseClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimerEditorScreenPreview() {
    AppTheme {
        TimerEditorScreenContent(
            state = TimerEditorScreenState(
                timerName = "Timer name",
                intervalList = listOf(
                    IntervalItemData(
                        id = 0,
                        name = "Interval name",
                        duration = "15:00",
                        color = IntervalColor.RED
                    ),
                    IntervalItemData(
                        id = 0,
                        name = "Interval name",
                        duration = "15:00",
                        color = IntervalColor.RED
                    )
                ),
                numberOfRounds = 4
            ),
            scaffoldState = rememberScaffoldState(),
            onTimerNameChanged = {},
            onIncreaseRoundsClick = {},
            onDecreaseRoundsClick = {},
            onIncreaseStartDelayClick = {},
            onDecreaseStartDelayClick = {},
            onIncreaseTimeBetweenRoundsClick = {},
            onDecreaseTimeBetweenRoundsClick = {},
            onIntervalClick = {},
            onAddIntervalClick = {},
            onIntervalDialogConfirmClick = { _, _, _ -> },
            onIntervalDialogCancelClick = {},
            onDeleteClick = {},
            onStartClick = {},
            onSaveClick = {}
        )
    }
}
