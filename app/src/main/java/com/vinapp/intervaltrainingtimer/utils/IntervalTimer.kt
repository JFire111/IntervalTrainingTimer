package com.vinapp.intervaltrainingtimer.utils

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.TimerEntity
import kotlinx.coroutines.*

abstract class IntervalTimer {

    private var timer: TimerEntity? = null
    private var startDelay: Long = 0L
    private var stepInMillis: Long = 100
    private var timerJob: Job? = null
    private var isPaused: Boolean = false
    private var remainingDelayTime: Long = 0L
    private var remainingTime: Long = 0L
    private var remainingRounds: Int = 0
    private var currentIntervalIndex: Int = 0
    private var remainingIntervalTime: Long = 0L

    fun setStartDelay(delay: Long) {
        this.startDelay = delay
    }

    fun start(timer: TimerEntity, stepInMillis: Long = 100) {
        this.timer = timer
        this.stepInMillis = stepInMillis
        isPaused = false
//        remainingTime = this.timer!!.getDurationInMillis()
        remainingRounds = this.timer!!.numberOfRounds
        timerJob = MainScope().launch {
            runDelay()
            runTimer()
        }
    }

    fun pause() {
        timerJob!!.cancel()
        isPaused = true
    }

    fun resume() {
        isPaused = false
        timerJob = MainScope().launch {
            if (remainingDelayTime > 0L) {
                runDelay()
            }
            runTimer()
        }
    }

    fun stop() {
        timerJob!!.cancel()
        isPaused = false
//        remainingTime = timer!!.getDurationInMillis()
        remainingRounds = timer!!.numberOfRounds
        currentIntervalIndex = 0
        remainingIntervalTime = 0L
    }

    abstract fun onDelayTick(delay: Long)

    abstract fun onTick(time: Long)

    abstract fun onIntervalStart(intervalIndex: Int)

    abstract fun onIntervalEnd(endedIntervalIndex: Int)

    abstract fun onRoundStart(remainingRounds: Int)

    abstract fun onRoundEnd(remainingRounds: Int)

    abstract fun onFinish()

    private suspend fun runDelay() {
        remainingDelayTime = startDelay
        while (remainingDelayTime > 0L) {
            remainingDelayTime -= computeTimeDifference()
            onDelayTick(remainingDelayTime)
        }
    }

    private suspend fun runTimer() {
        while (remainingRounds > 0) {
            onRoundStart(remainingRounds)
            var intervals = getIntervals()
            intervals.forEach { interval ->
                remainingIntervalTime = getRemainingTime(interval)
                onIntervalStart(currentIntervalIndex)
                while (remainingIntervalTime > 0L) {
                    var difference = computeTimeDifference()
                    remainingIntervalTime -= difference
                    remainingTime -= difference
                    onTick(remainingTime)
                }
                onIntervalEnd(currentIntervalIndex)
                currentIntervalIndex++
            }
            remainingRounds--
            onRoundEnd(remainingRounds)
        }
        onFinish()
    }

    private suspend fun computeTimeDifference(): Long {
        val beforeDelay = System.currentTimeMillis()
        delay(stepInMillis)
        return System.currentTimeMillis() - beforeDelay
    }

    private fun getIntervals(): List<Interval> {
        return listOf()
//        if (isPaused) {
//            timer!!.duration.asSequence().drop(currentIntervalIndex).toList()
//        } else {
//            currentIntervalIndex = 0
//            timer!!.duration
//        }
    }

    private fun getRemainingTime(currentInterval: Interval): Long {
        return if (isPaused) {
            isPaused = false
            remainingIntervalTime
        } else {
            currentInterval.getDurationInMillis()
        }
    }
}