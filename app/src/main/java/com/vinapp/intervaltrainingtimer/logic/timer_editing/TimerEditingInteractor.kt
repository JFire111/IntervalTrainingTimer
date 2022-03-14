package com.vinapp.intervaltrainingtimer.logic.timer_editing

import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerEditingInteractor(private val timerRepository: TimerMVPModel, private var timerEditingOutput: TimerEditingOutput?): TimerEditingInput {

    private var currentTimer: Timer? = null

    override fun saveTimer(timer: Timer) {
        var timerId = timer.id
        if (timerId != null) {
            timer.updatedTime = System.currentTimeMillis()
            timerRepository.updateTimer(timer)
            timerEditingOutput?.provideTimer(timer)
        } else {
            timerId = getUniqueId(1)
            timerRepository.addTimer(timer.copy(id = timerId, createdTime = System.currentTimeMillis()))
        }
        timerEditingOutput?.provideTimer(timerRepository.getTimerById(timerId)!!)
    }

    override fun setTimer(timer: Timer) {
        timerEditingOutput?.provideTimer(timer)
    }

    override fun registerOutput(output: TimerEditingOutput) {
        timerEditingOutput = output
        if (currentTimer != null) {
            output.provideTimer(currentTimer!!)
        }
    }

    override fun unregisterOutput() {
        timerEditingOutput = null
    }

    private fun getUniqueId(defaultId: Int): Int {
        var id = defaultId
        while (timerRepository.getTimerById(id) != null) {
            id++
        }
        return id
    }
}