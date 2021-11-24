package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerEditingOutput {

    fun provideNumberOfRounds(numberOfRounds: Int)

    fun provideIntervals(intervals: List<Interval>)

    fun provideTimer(timer: Timer)
}