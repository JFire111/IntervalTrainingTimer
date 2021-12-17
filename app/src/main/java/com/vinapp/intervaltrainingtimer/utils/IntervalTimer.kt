package com.vinapp.intervaltrainingtimer.utils

import com.vinapp.intervaltrainingtimer.entities.Interval
import com.vinapp.intervaltrainingtimer.entities.Timer
import kotlinx.coroutines.*

abstract class IntervalTimer(val timer: Timer, val stepInMillis: Long = 1000) {

    private var timerJob: Job? = null
    private var isPaused: Boolean = false
    private var remainingTime: Long = 0L
    private var remainingRounds: Int = 0
    private var currentIntervalIndex: Int = 0
    private var remainingIntervalTime: Long = 0L

    fun start() {
        isPaused = false
        remainingTime = timer.getDurationInMillis()
        remainingRounds = timer.numberOfRounds
        timerJob = MainScope().launch {
            run()
        }
    }

    fun pause() {
        timerJob!!.cancel()
        isPaused = true
    }

    fun resume() {
        timerJob = MainScope().launch {
            run()
        }
    }

    fun stop() {
        timerJob!!.cancel()
        isPaused = false
        remainingTime = timer.getDurationInMillis()
        remainingRounds = timer.numberOfRounds
        currentIntervalIndex = 0
        remainingIntervalTime = 0L
    }

    abstract fun onTick(time: Long)

    abstract fun onIntervalStart(endedIntervalIndex: Int)

    abstract fun onRoundEnded(remainingRounds: Int)

    abstract fun onFinish()

    private suspend fun run() = coroutineScope {
        launch {
            while (remainingRounds > 0) {
                var intervals = getIntervals()
                intervals.forEach { interval ->
                    remainingIntervalTime = getRemainingTime(interval)
                    onIntervalStart(currentIntervalIndex)
                    while (remainingIntervalTime > 0L) {
                        var beforeDelay = System.currentTimeMillis()
                        delay(stepInMillis)
                        var difference = System.currentTimeMillis() - beforeDelay
                        remainingIntervalTime -= difference
                        remainingTime -= difference
                        onTick(remainingTime)
                    }
                    currentIntervalIndex++
                }
                remainingRounds--
                onRoundEnded(remainingRounds)
            }
            onFinish()
        }
    }

    private fun getIntervals(): List<Interval> {
        return if (isPaused) {
            timer.intervals.asSequence().drop(currentIntervalIndex).toList()
        } else {
            currentIntervalIndex = 0
            timer.intervals
        }
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