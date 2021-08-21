package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer

interface TimerEditing {

    fun createTimer(intervals: List<Interval>)

    fun getTimer(position: Int): Timer

    fun editTimer(timer: Timer, newIntervals: List<Interval>)
}