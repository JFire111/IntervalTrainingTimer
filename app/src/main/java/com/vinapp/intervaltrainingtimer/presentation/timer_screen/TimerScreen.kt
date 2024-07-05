package com.vinapp.intervaltrainingtimer.presentation.timer_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.domain.timer.TimerState
import com.vinapp.intervaltrainingtimer.ui_components.bottom_buttons_container.BottomButtonsContainer
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme
import com.vinapp.intervaltrainingtimer.ui_components.timer.Timer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun TimerScreen(
    timerId: String,
) {
    val viewModel:TimerScreenViewModel = viewModel(
        factory = TimerScreenViewModel.Factory
    )
    val state by viewModel.screenStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        launch {
            viewModel.screenActionFlow.collect { action ->
                when (action) {}
            }
        }
        viewModel.loadTimer(timerId)
    }

    TimerScreenContent(
        state = state,
        onCenterActionButtonClick = viewModel::onCenterActionButtonClick
    )
}

@Composable
private fun TimerScreenContent(
    state: TimerScreenState,
    onCenterActionButtonClick: () -> Unit
) {
    BottomButtonsContainer(
        showCenterButton = true,
        onCenterButtonClick = onCenterActionButtonClick,
        centerButtonIcon = when (state.timerState) {
            TimerState.IN_PROGRESS -> R.drawable.ic_pause
            else -> R.drawable.ic_play
        },
        centerButtonBaseColor = AppTheme.colors.mediumGray,
        centerButtonIconColor = when (state.backgroundColor) {
            IntervalColor.GREEN -> AppTheme.colors.green
            IntervalColor.RED -> AppTheme.colors.red
            IntervalColor.YELLOW -> AppTheme.colors.yellow
            IntervalColor.WHITE -> AppTheme.colors.white
        }
    ) {
        Scaffold(
            backgroundColor = when (state.backgroundColor) {
                IntervalColor.GREEN -> AppTheme.colors.green
                IntervalColor.RED -> AppTheme.colors.red
                IntervalColor.YELLOW -> AppTheme.colors.yellow
                IntervalColor.WHITE -> AppTheme.colors.white
            }
        ) {
            Timer(
                modifier = Modifier
                    .padding(it)
                    .padding(64.dp),
                remainingTimerInMillisState = state.remainingTimeFlow.collectAsState(),
                intervalProgressInPercentState = state.intervalProgressFlow.collectAsState()
            )
        }
    }
}

@Preview
@Composable
private fun TimerScreenPreview() {
    AppTheme {
        TimerScreenContent(
            state = TimerScreenState(
                timerName = "Timer name",
                backgroundColor = IntervalColor.RED,
                remainingTimeFlow = MutableStateFlow(900)
            ),
            onCenterActionButtonClick = {}
        )
    }
}