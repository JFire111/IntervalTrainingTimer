package com.vinapp.intervaltrainingtimer.logic.gettimers

import com.vinapp.intervaltrainingtimer.data.repositories.TimerRepository
import com.vinapp.intervaltrainingtimer.entities.base.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class GetTimersInteractor(private val timerRepository: TimerMVPModel): GetTimers {

    override fun getTimersList(): List<Timer> {
        return timerRepository.getTimers()
    }

    override fun deleteTimer(timer: Timer) {
        timerRepository.deleteTimer(timer)
    }
}