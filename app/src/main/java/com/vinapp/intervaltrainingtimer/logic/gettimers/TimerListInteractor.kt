package com.vinapp.intervaltrainingtimer.logic.gettimers

import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerListInteractor(private val timerRepository: TimerMVPModel, private var timerListOutput: TimerListOutput?): TimerListInput {

    private var selectedTimer: Timer? = null

    override fun openTimerList() {
        timerListOutput?.provideTimers(timerRepository.getTimers())
    }

    override fun selectTimer(timerPosition: Int?) {
        if (timerPosition == null) {
            selectedTimer = null
        } else {
            selectedTimer = timerRepository.getTimers()[timerPosition]
        }
    }

    override fun deleteTimer(timer: Timer) {
        timerRepository.deleteTimer(timer)
    }

    fun registerOutput(output: TimerListOutput) {
        timerListOutput = output
        timerListOutput?.provideTimers(timerRepository.getTimers())
    }
}