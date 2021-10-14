package com.vinapp.intervaltrainingtimer.logic.gettimers

interface TimerListInput {

    fun openTimerList()

    fun selectTimer(timerPosition: Int?)

    fun deleteTimer(timerId: Int)
}