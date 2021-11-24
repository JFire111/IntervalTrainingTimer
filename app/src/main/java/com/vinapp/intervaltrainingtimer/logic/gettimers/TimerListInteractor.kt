package com.vinapp.intervaltrainingtimer.logic.gettimers

import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerListInteractor(private val timerRepository: TimerMVPModel, private var timerListOutput: TimerListOutput?): TimerListInput {

    private var selectedTimer: Timer? = null

    override fun openTimerList() {
        timerListOutput?.provideTimers(timerRepository.getTimers())
    }

    override fun selectTimer(timer: Timer?) {
        selectedTimer = timer
    }

    override fun deleteTimer(timerId: Int) {
        timerRepository.deleteTimer(timerId)
        timerListOutput?.provideTimers(timerRepository.getTimers())
    }

    fun registerOutput(output: TimerListOutput) {
        timerListOutput = output
        timerListOutput?.provideTimers(timerRepository.getTimers())
    }
}