package com.vinapp.intervaltrainingtimer.ui.timer_editor_screen

import com.vinapp.intervaltrainingtimer.base.presentation.ScreenState
import com.vinapp.intervaltrainingtimer.domain.Interval
import com.vinapp.intervaltrainingtimer.ui_components.time_text.TimeDigits
import com.vinapp.intervaltrainingtimer.ui_components.interval_item.IntervalItemData

data class TimerEditorScreenState(
    val timerName: String? = null,
    val isTimerNameError: Boolean = false,
    val totalTimeDigits: TimeDigits = TimeDigits(),
    val intervalList: List<IntervalItemData>? = null,
    val numberOfRounds: Int = 1,
    val startDelay: Int = 0,
    val timeBetweenRounds: Int = 0,
    val selectedInterval: Interval? = null,
    val showIntervalDialog: Boolean = false,
    val showDeleteButton: Boolean = false,
) : ScreenState