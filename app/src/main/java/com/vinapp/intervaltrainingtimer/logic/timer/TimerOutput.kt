package com.vinapp.intervaltrainingtimer.logic.timer

interface TimerOutput {

    fun provideState(state: TimerState)

    fun provideDelay(delay: Long)

    fun provideTime(remainingTime: Long, remainingIntervalTime: Long)

    fun provideCurrentInterval(intervalIndex: Int)
}