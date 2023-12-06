package com.vinapp.intervaltrainingtimer.presentation.timer_list_screen

import com.vinapp.intervaltrainingtimer.base.presentation.ScreenAction

sealed interface TimerListScreenAction : ScreenAction {
    data class NavigateToTimerEditorScreen(
        val timerId: String?
    ) : TimerListScreenAction
    data class NavigateToTimerScreen(
        val timerId: String
    ) : TimerListScreenAction
}