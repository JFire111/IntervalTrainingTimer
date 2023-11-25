package com.vinapp.intervaltrainingtimer.ui.timer_list_screen

import com.vinapp.intervaltrainingtimer.base.presentation.ScreenAction

sealed interface TimerListScreenAction : ScreenAction {
    data class NavigateToTimerEditorScreen(
        val timerId: String?
    ) : TimerListScreenAction
}