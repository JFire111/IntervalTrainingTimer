package com.vinapp.intervaltrainingtimer.logic.gettimers

import com.vinapp.intervaltrainingtimer.entities.base.Timer

interface TimerListOutput {

    fun provideTimers(timers: List<Timer>)
}