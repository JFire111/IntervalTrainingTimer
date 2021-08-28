package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.entities.TrainingTimer
import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerEditingInteractor(private val timerRepository: TimerMVPModel): TimerEditing {

    override fun createTimer(intervals: List<Interval>) {
        timerRepository.addTimer(TrainingTimer(123, "NAME", intervals.toList()))
    }

    override fun getTimer(position: Int): Timer {
        return timerRepository.getTimers()[position]
    }

    override fun editTimer(timer: Timer, newIntervals: List<Interval>) {
        timer.intervals = newIntervals
        timerRepository.updateTimer(timer)
    }
}