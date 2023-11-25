package com.vinapp.intervaltrainingtimer.logic.timer_editing

import com.vinapp.intervaltrainingtimer.entities.TimerEntity

interface TimerEditingOutput {

    fun provideTimer(timer: TimerEntity)
}