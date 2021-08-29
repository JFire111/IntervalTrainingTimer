package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.entities.base.Interval

interface TimerEditingOutput {

    fun provideIntervals(intervals: List<Interval>)
}