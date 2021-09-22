package com.vinapp.intervaltrainingtimer.data.repositories

import com.vinapp.intervaltrainingtimer.entities.TrainingTimer
import com.vinapp.intervaltrainingtimer.entities.base.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerRepository: TimerMVPModel {

    var timerList: ArrayList<Timer> = arrayListOf(
            TrainingTimer(0, "qwe", 1, listOf()),
            TrainingTimer(1, "asd", 1, listOf()),
            TrainingTimer(2, "zxc", 1, listOf()))

    override fun addTimer(timer: Timer) {
        timerList.add(timer)
    }

    override fun getTimers(): ArrayList<Timer> {
        return timerList
    }

    override fun getTimerById(id: Int): Timer? {
        return timerList.find { it.id == id }
    }

    override fun updateTimer(timer: Timer) {
        var index = timerList.indexOfFirst { it.id == timer.id }
        timerList[index] = timer
    }

    override fun deleteTimer(timer: Timer) {}
}