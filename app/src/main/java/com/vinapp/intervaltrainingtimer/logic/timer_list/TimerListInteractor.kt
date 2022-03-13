package com.vinapp.intervaltrainingtimer.logic.timer_list

import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerListInteractor(private val timerRepository: TimerMVPModel, private var timerListOutput: TimerListOutput?): TimerListInput {

    override fun getTimerList() {
        timerListOutput?.provideTimers(timerRepository.getTimers())
    }

    override fun deleteTimer(timerId: Int) {
        timerRepository.deleteTimer(timerId)
        timerListOutput?.provideTimers(timerRepository.getTimers())
    }

    override fun registerOutput(output: TimerListOutput) {
        timerListOutput = output
        timerListOutput?.provideTimers(timerRepository.getTimers())
    }

    override fun unregisterOutput() {
        timerListOutput = null
    }
}