package com.vinapp.intervaltrainingtimer.logic.timerediting

import com.vinapp.intervaltrainingtimer.entities.Timer
import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.mvp.model.TimerMVPModel
import kotlin.random.Random

class TimerEditingInteractor(private val timerRepository: TimerMVPModel, private var timerEditingOutput: TimerEditingOutput?): TimerEditingInput {

    private var timer: Timer? = null
    private var numberOfRounds: Int = 1
    private val intervalList: ArrayList<Interval> = arrayListOf()

    override fun saveTimer() {
        if (timer != null) {
            timer!!.numberOfRounds = numberOfRounds
            timer!!.intervals = intervalList.toList()
            timer!!.updatedTime = System.currentTimeMillis()
            timerRepository.updateTimer(timer!!)
        } else {
            timerRepository.addTimer(Timer(getUniqueId(1), "NAME", numberOfRounds, intervalList.toList(), System.currentTimeMillis(), null))
        }
        timer = null
        numberOfRounds = 1
        intervalList.clear()
        provideNewValues()
    }

    override fun addRound() {
        numberOfRounds++
        timerEditingOutput?.provideNumberOfRounds(numberOfRounds)
    }

    override fun removeRound() {
        if (numberOfRounds > 1) {
            numberOfRounds--
            timerEditingOutput?.provideNumberOfRounds(numberOfRounds)
        }
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

    override fun deleteInterval(intervalPosition: Int) {
        intervalList.removeAt(intervalPosition)
        timerEditingOutput?.provideIntervals(intervalList)
    }

    override fun setTimerForEditing(timer: Timer?) {
        this.timer = timer
        if (intervalList.isNotEmpty()) {
            intervalList.clear()
        }
        if (this.timer != null) {
            numberOfRounds = timer!!.numberOfRounds
            intervalList.addAll(this.timer!!.intervals)
            provideNewValues()
        }
    }

    override fun cancelEditing() {
        this.timer = null
        clear()
    }

    override fun clear() {
        numberOfRounds = 1
        intervalList.clear()
        provideNewValues()
    }

    fun registerOutput(output: TimerEditingOutput) {
        timerEditingOutput = output
        provideNewValues()
    }

    private fun provideNewValues() {
        timerEditingOutput?.provideIntervals(intervalList)
        timerEditingOutput?.provideNumberOfRounds(numberOfRounds)
    }

    private fun getUniqueId(defaultId: Int): Int {
        var id = defaultId
        while (timerRepository.getTimerById(id) != null) {
            id++
        }
        return id
    }
}