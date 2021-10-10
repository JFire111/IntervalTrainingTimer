package com.vinapp.intervaltrainingtimer.logic.gettimers

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerListOutput {

    fun provideTimers(timers: List<Timer>)
}