package com.vinapp.intervaltrainingtimer.utils

interface IntervalTimerControl {
    fun onTick(time: Long)
    fun onIntervalChanged(intervalIndex: Int?)
    fun onRoundChanged(currentRound: Int)
    fun onFinish()
}