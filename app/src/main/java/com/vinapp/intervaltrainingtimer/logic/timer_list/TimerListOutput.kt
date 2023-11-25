package com.vinapp.intervaltrainingtimer.logic.timer_list

import com.vinapp.intervaltrainingtimer.entities.TimerEntity

interface TimerListOutput {

    fun provideTimers(timers: List<TimerEntity>)
}