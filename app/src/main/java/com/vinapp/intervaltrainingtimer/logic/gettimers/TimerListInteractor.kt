package com.vinapp.intervaltrainingtimer.logic.gettimers

import com.vinapp.intervaltrainingtimer.entities.base.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerListInteractor(private val timerRepository: TimerMVPModel, private var timerListOutput: TimerListOutput?): TimerListInput {

    override fun openTimerList() {
        timerListOutput?.provideTimers(timerRepository.getTimers())
    }

    override fun deleteTimer(timer: Timer) {
        timerRepository.deleteTimer(timer)
    }

    fun registerOutput(timerListOutput: TimerListOutput) {
        this.timerListOutput = timerListOutput
    }
}