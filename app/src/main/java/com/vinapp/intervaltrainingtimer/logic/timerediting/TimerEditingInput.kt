package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerEditingInput {

    fun saveTimer(timer: Timer)

    fun registerOutput(output: TimerEditingOutput)

    fun unregisterOutput()
}