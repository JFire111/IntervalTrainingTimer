package com.vinapp.intervaltrainingtimer.logic.timer_editing

import com.vinapp.intervaltrainingtimer.entities.Timer

interface TimerEditingOutput {

    fun provideTimer(timer: Timer)
}