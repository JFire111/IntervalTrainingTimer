package com.vinapp.intervaltrainingtimer.utils

import kotlinx.coroutines.*
import java.lang.Runnable

abstract class IntervalTimer() {

    private val timerStepInMillis: Long = 1000
    var inProcess = true
    private var timerJob: Job? = null

    fun start() {
        var time: Long = 0
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
        var result = { x: Int ->
            x.toString()
        }
        result
    }

    abstract fun onTick()

    abstract fun onIntervalFinish()

    abstract fun onFinish()

    private suspend fun run() = coroutineScope{
        var time: Long = 0
        launch {
            while (time != 6000L) {
                delay(timerStepInMillis)
                time += timerStepInMillis
                onTick()
            }
        }
    }
}