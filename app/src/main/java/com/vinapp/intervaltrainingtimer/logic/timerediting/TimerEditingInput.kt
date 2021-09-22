package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer

interface TimerEditingInput {

    fun saveTimer()

    fun addRound()

    fun removeRound()

    fun addInterval(interval: Interval)

    fun getInterval(intervalPosition: Int): Interval

    fun updateInterval(position: Int, interval: Interval)

    fun setTimerForEditing(timer: Timer?)

    fun clearTimer()
}