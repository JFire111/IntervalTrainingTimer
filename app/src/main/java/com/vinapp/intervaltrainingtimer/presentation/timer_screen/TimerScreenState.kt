package com.vinapp.intervaltrainingtimer.presentation.timer_screen

import com.vinapp.intervaltrainingtimer.base.presentation.ScreenState
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.domain.timer.TimerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class TimerScreenState(
    val timerName: String = "",
    val backgroundColor: IntervalColor = IntervalColor.WHITE,
    val timerState: TimerState = TimerState.INITIALIZED,
    val remainingTimeFlow: StateFlow<Long> = MutableStateFlow(0),
    val intervalProgressFlow: StateFlow<Float> = MutableStateFlow(0.0F),
) : ScreenState