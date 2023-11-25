package com.vinapp.intervaltrainingtimer.logic.timer_editing

import com.vinapp.intervaltrainingtimer.entities.TimerEntity

interface TimerEditingInput {

    fun saveTimer(timer: TimerEntity)

    fun setTimer(timer: TimerEntity)

    fun registerOutput(output: TimerEditingOutput)

    fun unregisterOutput()
}