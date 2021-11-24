package com.vinapp.intervaltrainingtimer.logic.timer

import com.vinapp.intervaltrainingtimer.entities.Interval

interface TimerOutput {

    fun provideTime(remainingTime: Int)

    fun provideCurrentInterval(interval: Interval)
}