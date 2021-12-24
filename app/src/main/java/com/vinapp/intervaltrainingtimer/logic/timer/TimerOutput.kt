package com.vinapp.intervaltrainingtimer.logic.timer

interface TimerOutput {

    fun provideState(state: TimerState)

    fun provideTime(remainingTime: Long)

    fun provideCurrentInterval(intervalIndex: Int)
}