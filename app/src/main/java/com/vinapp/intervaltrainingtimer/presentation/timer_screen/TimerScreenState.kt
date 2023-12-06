package com.vinapp.intervaltrainingtimer.presentation.timer_screen

import com.vinapp.intervaltrainingtimer.base.presentation.ScreenState
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.domain.timer.TimerState

data class TimerScreenState(
    val timerName: String = "",
    val backgroundColor: IntervalColor = IntervalColor.WHITE,
    val remainingTime: String = "",
    var timerState: TimerState = TimerState.INITIALIZED
) : ScreenState