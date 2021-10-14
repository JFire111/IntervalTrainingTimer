package com.vinapp.intervaltrainingtimer.data.repositories

import com.vinapp.intervaltrainingtimer.data.database.TimerDao
import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerRepository(private val timerDao: TimerDao): TimerMVPModel {

    override fun addTimer(timer: Timer) {
        timerDao.insert(timer)
    }

    override fun getTimers(): List<Timer> {
        return timerDao.getAll()
    }

    override fun getTimerById(id: Int): Timer? {
        return timerDao.getById(id)
    }

    override fun updateTimer(timer: Timer) {
        timerDao.update(timer)
    }

    override fun deleteTimer(id: Int) {
        timerDao.delete(id)
    }
}