package com.vinapp.intervaltrainingtimer.ui.timer_editor_screen

import com.vinapp.intervaltrainingtimer.base.presentation.ScreenAction

sealed interface TimerEditorScreenAction : ScreenAction {
    data class NavigateToTimerScreen(
        val timerId: String
    ) : TimerEditorScreenAction
    object NavigateBack : TimerEditorScreenAction
}