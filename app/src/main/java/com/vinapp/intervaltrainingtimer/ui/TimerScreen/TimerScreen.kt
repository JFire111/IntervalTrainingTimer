package com.vinapp.intervaltrainingtimer.ui.TimerScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.ui_components.bottom_buttons_container.BottomButtonsContainer
import com.vinapp.intervaltrainingtimer.ui_components.interval_item.IntervalItemData
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme
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
        onCenterButtonClick = onCenterActionButtonClick,
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
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.remainingTime,
                    style = AppTheme.typography.extraLarge.copy(
                        color = AppTheme.colors.darkGrayFontColor
                    )
                )
            }
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
                remainingTime = "15:00"
            ),
            onCenterActionButtonClick = {}
        )
    }
}