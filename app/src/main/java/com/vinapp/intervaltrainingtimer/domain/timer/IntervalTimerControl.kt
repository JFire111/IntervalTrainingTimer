package com.vinapp.intervaltrainingtimer.domain.timer

interface IntervalTimerControl {
    fun onTick(remainingTimerTime: Long, remainingIntervalTime: Long)
    fun onIntervalChanged(intervalIndex: Int?)
    fun onRoundChanged(currentRound: Int)
    fun onFinish()
}