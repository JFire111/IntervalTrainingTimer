package com.vinapp.intervaltrainingtimer.logic.timer_editing

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerEditingInput {

    fun saveTimer(timer: Timer)

    fun setTimer(timer: Timer)

    fun registerOutput(output: TimerEditingOutput)

    fun unregisterOutput()
}