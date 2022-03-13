package com.vinapp.intervaltrainingtimer.logic.timer_list

interface TimerListInput {

    fun getTimerList()

    fun deleteTimer(timerId: Int)

    fun registerOutput(output: TimerListOutput)

    fun unregisterOutput()
}