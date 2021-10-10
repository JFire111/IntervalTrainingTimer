package com.vinapp.intervaltrainingtimer.logic.gettimers

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerListInput {

    fun openTimerList()

    fun selectTimer(timerPosition: Int?)

    fun deleteTimer(timer: Timer)
}