package com.vinapp.intervaltrainingtimer.logic.timer

import com.vinapp.intervaltrainingtimer.entities.Interval

interface TimerOutput {

    fun provideState(state: TimerState)

    fun provideTime(remainingTime: Long)

    fun provideCurrentInterval(interval: Interval)
}