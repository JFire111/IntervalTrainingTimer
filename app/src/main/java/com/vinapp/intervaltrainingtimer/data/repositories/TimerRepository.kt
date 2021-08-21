package com.vinapp.intervaltrainingtimer.data.repositories

import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerRepository: TimerMVPModel {

    var timerList: ArrayList<Timer> = arrayListOf(
            object : Timer {override var name = "qwe"
                override var intervals: List<Interval>
                    get() = listOf()
                    set(value) {}
            },
            object : Timer {override var name = "asd"
                override var intervals: List<Interval>
                    get() = listOf()
                    set(value) {}
            },
            object : Timer {override var name = "zxc"
                override var intervals: List<Interval>
                    get() = listOf()
                    set(value) {}
            })

    override fun addTimer(timer: Timer) {
        timerList.add(timer)
    }

    override fun getTimers(): ArrayList<Timer> {
        return timerList
    }

    override fun updateTimer(timer: Timer) {
    }

    override fun deleteTimer(timer: Timer) {}
}