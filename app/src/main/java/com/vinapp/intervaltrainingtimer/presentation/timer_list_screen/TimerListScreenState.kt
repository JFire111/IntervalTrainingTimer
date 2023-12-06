package com.vinapp.intervaltrainingtimer.presentation.timer_list_screen

import com.vinapp.intervaltrainingtimer.base.presentation.ScreenState
import com.vinapp.intervaltrainingtimer.ui_components.timer_item.TimerItemData

data class TimerListScreenState(
    val timerList: List<TimerItemData>? = null,
    val selectedItem: Int? = null,
    val showDeleteButton: Boolean = false,
    val showStartButton: Boolean = false,
    val showEditButton: Boolean = false,
) : ScreenState