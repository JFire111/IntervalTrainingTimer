package com.vinapp.intervaltrainingtimer.utils

import com.vinapp.intervaltrainingtimer.entities.Timer
import kotlinx.coroutines.*

abstract class IntervalTimer(val timer: Timer, val stepInMillis: Long = 1000) {

    private var timerJob: Job? = null
    private var isPaused: Boolean = false

    fun start() {
        timerJob = MainScope().launch {
            run()
        }
    }

    fun pause() {
        timerJob!!.cancel()
    }

    fun resume() {
    }

    fun stop() {
    }

    abstract fun onTick(remainingTime: Long)

    abstract fun onIntervalEnded(finishedIntervalIndex: Int)

    abstract fun onRoundEnded(remainingRounds: Int)

    abstract fun onFinish()

    private suspend fun run() = coroutineScope {
        var remainingTime: Long = timer.getDurationInMillis()
        var remainingRounds: Int = timer.numberOfRounds
        launch {
            while (remainingRounds > 0) {
                timer.intervals.forEachIndexed { index, interval ->
                    var remainingIntervalTime = interval.getDurationInMillis()
                    while (remainingIntervalTime > 0L) {
                        delay(stepInMillis)
                        remainingIntervalTime -= stepInMillis
                        remainingTime -= stepInMillis
                        onTick(remainingTime)
                    }
                    onIntervalEnded(index)
                }
                remainingRounds--
                onRoundEnded(remainingRounds)
            }
            onFinish()
        }
    }
}