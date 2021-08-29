package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.data.repositories.IntervalRepository
import com.vinapp.intervaltrainingtimer.entities.TrainingTimer
import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.mvp.model.IntervalMVPModel
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerEditingInteractor(private val timerRepository: TimerMVPModel, private var timerEditingOutput: TimerEditingOutput?): TimerEditingInput {

    private val intervalRepository: IntervalMVPModel = IntervalRepository()

    override fun createTimer() {
        timerRepository.addTimer(TrainingTimer(123, "NAME", intervalRepository.getIntervals()))
        intervalRepository.clearIntervals()
        timerEditingOutput?.provideIntervals(intervalRepository.getIntervals())
    }

    override fun addInterval(interval: Interval) {
        intervalRepository.addInterval(interval)
        timerEditingOutput?.provideIntervals(intervalRepository.getIntervals())
    }

    override fun getInterval(intervalPosition: Int): Interval {
        return intervalRepository.getIntervals()[intervalPosition]
    }

    override fun updateInterval(position: Int, interval: Interval) {
        intervalRepository.updateInterval(position, interval)
        timerEditingOutput?.provideIntervals(intervalRepository.getIntervals())
    }

    fun registerOutput(output: TimerEditingOutput) {
        timerEditingOutput = output
        timerEditingOutput?.provideIntervals(intervalRepository.getIntervals())
    }
}