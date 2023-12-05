package com.vinapp.intervaltrainingtimer.ui.TimerScreen

import com.vinapp.intervaltrainingtimer.base.presentation.ScreenState
import com.vinapp.intervaltrainingtimer.common.IntervalColor
import com.vinapp.intervaltrainingtimer.logic.timer.TimerState
import com.vinapp.intervaltrainingtimer.ui_components.interval_item.IntervalItemData

data class TimerScreenState(
    val timerName: String = "",
    val backgroundColor: IntervalColor = IntervalColor.WHITE,
    val remainingTime: String = "",
    var timerState: TimerState = TimerState.INITIALIZED
) : ScreenState