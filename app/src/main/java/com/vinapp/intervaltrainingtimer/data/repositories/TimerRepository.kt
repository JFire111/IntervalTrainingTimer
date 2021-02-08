package com.vinapp.intervaltrainingtimer.data.repositories

import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerModel

class TimerRepository: TimerModel {

    override fun addTimer(timer: Timer) {}

    override fun getTimers(): List<Timer> {
        return listOf(
                object : Timer {override var name = "qwe" },
                object : Timer {override var name = "asd" },
                object : Timer {override var name = "zxc" })
    }

    override fun deleteTimer(timer: Timer) {}
}