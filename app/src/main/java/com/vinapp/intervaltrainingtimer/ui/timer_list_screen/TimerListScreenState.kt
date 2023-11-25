package com.vinapp.intervaltrainingtimer.ui.timer_list_screen

import com.vinapp.intervaltrainingtimer.base.presentation.ScreenState
import com.vinapp.intervaltrainingtimer.ui_components.timer_item.TimerItemData

data class TimerListScreenState(
    val timerList: List<TimerItemData>? = null
) : ScreenState