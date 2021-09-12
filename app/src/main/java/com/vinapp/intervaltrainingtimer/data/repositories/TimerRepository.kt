package com.vinapp.intervaltrainingtimer.data.repositories

import com.vinapp.intervaltrainingtimer.entities.TrainingTimer
import com.vinapp.intervaltrainingtimer.entities.base.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerRepository: TimerMVPModel {

    var timerList: ArrayList<Timer> = arrayListOf(
            TrainingTimer(0, "qwe", listOf()),
            TrainingTimer(1, "asd", listOf()),
            TrainingTimer(2, "zxc", listOf()))

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
    }

    override fun deleteTimer(timer: Timer) {}
}