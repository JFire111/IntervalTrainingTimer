package com.vinapp.intervaltrainingtimer.data.repositories

import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerModel

class TimerRepository: TimerModel {

    var timerList: ArrayList<Timer> = arrayListOf(
            object : Timer {override var name = "qwe" },
            object : Timer {override var name = "asd" },
            object : Timer {override var name = "zxc" })

    override fun addTimer(timer: Timer) {
        timerList.add(timer)
    }

    override fun getTimers(): ArrayList<Timer> {
        return timerList
    }

    override fun deleteTimer(timer: Timer) {}
}