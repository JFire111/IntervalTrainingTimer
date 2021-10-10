package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerEditingInput {

    fun saveTimer()

    fun addRound()

    fun removeRound()

    fun addInterval(interval: Interval)

    fun getInterval(intervalPosition: Int): Interval

    fun updateInterval(position: Int, interval: Interval)

    fun setTimerForEditing(timer: Timer?)

    fun cancelEditing()

    fun clear()
}