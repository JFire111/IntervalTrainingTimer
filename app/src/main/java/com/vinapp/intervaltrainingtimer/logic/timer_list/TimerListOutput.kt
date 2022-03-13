package com.vinapp.intervaltrainingtimer.logic.timer_list

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerListOutput {

    fun provideTimers(timers: List<Timer>)
}