package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.entities.TrainingTimer
import com.vinapp.intervaltrainingtimer.entities.base.Interval
import com.vinapp.intervaltrainingtimer.entities.base.Timer
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel

class TimerEditingInteractor(private val timerRepository: TimerMVPModel, private var timerEditingOutput: TimerEditingOutput?): TimerEditingInput {

    private var timer: Timer? = null
    private val intervalList: ArrayList<Interval> = arrayListOf()

    override fun saveTimer() {
        if (timer != null) {
            timer!!.intervals = intervalList.toList()
            timerRepository.updateTimer(timer!!)
        } else {
            timerRepository.addTimer(TrainingTimer(timerRepository.getTimers().size + 1, "NAME", intervalList.toList()))
        }
        intervalList.clear()
        timerEditingOutput?.provideIntervals(intervalList)
    }

    override fun addInterval(interval: Interval) {
        intervalList.add(interval)
        timerEditingOutput?.provideIntervals(intervalList)
    }

    override fun getInterval(intervalPosition: Int): Interval {
        return intervalList[intervalPosition]
    }

    override fun updateInterval(position: Int, interval: Interval) {
        intervalList[position] = interval
        timerEditingOutput?.provideIntervals(intervalList)
    }

    override fun setTimerForEditing(timer: Timer?) {
        this.timer = timer
        if (intervalList.isNotEmpty()) {
            intervalList.clear()
        }
        if (this.timer != null) {
            intervalList.addAll(this.timer!!.intervals)
            timerEditingOutput?.provideIntervals(intervalList)
        }
    }

    fun registerOutput(output: TimerEditingOutput) {
        timerEditingOutput = output
        timerEditingOutput?.provideIntervals(intervalList)
    }
}