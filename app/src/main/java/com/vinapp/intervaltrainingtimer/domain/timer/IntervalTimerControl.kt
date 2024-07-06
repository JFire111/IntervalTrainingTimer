package com.vinapp.intervaltrainingtimer.domain.timer

interface IntervalTimerControl {
    fun onTick(remainingTimerTime: Long, remainingIntervalTime: Long)
    fun onStartDelayEnded()
    fun onIntervalChanged(intervalIndex: Int)
    fun onRoundEnded(nextRound: Int)
    fun onFinish()
}