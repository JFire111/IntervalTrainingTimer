package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.entities.base.Interval

interface TimerEditingInput {

    fun createTimer()

    fun addInterval(interval: Interval)

    fun getInterval(intervalPosition: Int): Interval

    fun updateInterval(position: Int, interval: Interval)
}