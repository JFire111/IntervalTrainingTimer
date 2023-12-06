package com.vinapp.intervaltrainingtimer.domain.timer

interface IntervalTimerControl {
    fun onTick(time: Long)
    fun onIntervalChanged(intervalIndex: Int?)
    fun onRoundChanged(currentRound: Int)
    fun onFinish()
}