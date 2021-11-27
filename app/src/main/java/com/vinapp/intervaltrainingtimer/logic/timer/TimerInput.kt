package com.vinapp.intervaltrainingtimer.logic.timer

interface TimerInput {

    fun start()

    fun pause()

    fun resume()

    fun stop()

    fun restart()

    fun registerOutput(output: TimerOutput)

    fun unregisterOutput()
}