package com.vinapp.intervaltrainingtimer.ui

sealed class Screen(val route: String) {
    object TimerListScreen : Screen("timer_list_screen")
    object TimerEditorScreen : Screen("timer_editor_screen")
    object TimerScreen : Screen("timer_screen")
}